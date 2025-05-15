package com.FoodSmart.FoodSmart_Backend.services;

import org.springframework.stereotype.Service;
import com.FoodSmart.FoodSmart_Backend.model.Products;
import org.apache.commons.text.similarity.JaroWinklerSimilarity;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ProductIntegrationService {

    private static final Logger logger = LoggerFactory.getLogger(ProductIntegrationService.class);
    
    private final ProductService openFoodFactsService;
    private final MercasticScraperService mercastic;
    private final JaroWinklerSimilarity jaroWinkler = new JaroWinklerSimilarity();
    private final LevenshteinDistance levenshtein = new LevenshteinDistance();
    
    // Umbral base de similitud - lo hacemos más flexible (0.75) y lo ajustamos dinámicamente
    private final double BASE_SIMILARITY_THRESHOLD = 0.75;
    
    // Palabras comunes que deben ignorarse para la comparación
    private final Set<String> commonWords = new HashSet<>(Arrays.asList(
        "de", "el", "la", "los", "las", "con", "sin", "para", "por", "en", "del",
        "marca", "producto", "paquete", "unidad", "caja", "envase", "botella"
    ));
    
    public ProductIntegrationService(ProductService openFoodFactsService, MercasticScraperService mercastic) {
        this.openFoodFactsService = openFoodFactsService;
        this.mercastic = mercastic;
    }

    public List<Products> getIntegratedProducts(String query) throws IOException {
        // Obtener productos de Open Food Facts y Mercastic
        List<Products> openFoodProducts = openFoodFactsService.searchProducts(query);
        List<Products> mercasticProducts = mercastic.searchProducts(query);
        
        List<Products> integratedProducts = new ArrayList<>();
        
        // Caso 1: Si tenemos productos en ambas fuentes, integramos
        if (!openFoodProducts.isEmpty() && !mercasticProducts.isEmpty()) {
            for (Products openFoodProduct : openFoodProducts) {
                // Buscar el producto más similar en Mercastic
                MatchResult matchResult = findBestMatch(openFoodProduct, mercasticProducts);
                Products bestMatch = matchResult.getProduct();
                
                if (bestMatch != null) {
                    // Integrar la información
                    openFoodProduct.setPrice(bestMatch.getPrice());
                    openFoodProduct.setSource("integrated");
                    openFoodProduct.setMatchConfidence(matchResult.getScore());
                    
                    // Usar imagen de Mercastic si la de Open Food Facts está vacía
                    if (openFoodProduct.getImageUrl() == null || openFoodProduct.getImageUrl().isEmpty()) {
                        openFoodProduct.setImageUrl(bestMatch.getImageUrl());
                    }
                }
                
                integratedProducts.add(openFoodProduct);
            }
        }
        // Caso 2: Si solo tenemos productos de Mercastic
        else if (openFoodProducts.isEmpty() && !mercasticProducts.isEmpty()) {
            for (Products mercasticProduct : mercasticProducts) {
                mercasticProduct.setSource("mercastic");
                integratedProducts.add(mercasticProduct);
            }
        }
        // Caso 3: Si solo tenemos productos de OpenFoodFacts
        else if (!openFoodProducts.isEmpty()) {
            for (Products openFoodProduct : openFoodProducts) {
                openFoodProduct.setSource("openFoodFacts");
                integratedProducts.add(openFoodProduct);
            }
        }
        
        return integratedProducts;
    }
    
    /**
     * Encuentra el mejor match para un producto entre una lista de candidatos
     */
    private MatchResult findBestMatch(Products targetProduct, List<Products> candidateProducts) {
        Products bestMatch = null;
        double highestScore = 0;
        
        String targetName = normalizeProductName(targetProduct.getProductName());
        
        // Si el nombre está vacío, no podemos hacer comparación
        if (targetName.isEmpty()) {
            return new MatchResult(null, 0);
        }
        
        // Calcular umbral dinámico basado en la longitud del nombre
        double threshold = calculateDynamicThreshold(targetName);
        
        for (Products candidate : candidateProducts) {
            String candidateName = normalizeProductName(candidate.getProductName());
            
            // Si el candidato tiene nombre vacío, saltamos
            if (candidateName.isEmpty()) {
                continue;
            }
            
            // Calcular puntuación compuesta (usando varios algoritmos)
            double score = calculateCompositeSimilarity(targetName, candidateName);
            
            // Si encontramos una coincidencia mejor que la actual y por encima del umbral
            if (score > highestScore && score >= threshold) {
                highestScore = score;
                bestMatch = candidate;
            }
        }
        
        return new MatchResult(bestMatch, highestScore);
    }
    
    /**
     * Normaliza el nombre de un producto para mejorar la comparación
     */
    private String normalizeProductName(String name) {
        if (name == null) return "";
        
        // Convertir a minúsculas
        String normalized = name.toLowerCase();
        
        // Eliminar caracteres no alfabéticos y dígitos (pero mantener espacios)
        normalized = normalized.replaceAll("[^a-zñáéíóúü\\s]", "");
        
        // Eliminar palabras comunes
        for (String word : commonWords) {
            // Eliminar la palabra si está rodeada de espacios o al principio/final
            normalized = normalized.replaceAll("\\b" + word + "\\b", "");
        }
        
        // Eliminar espacios múltiples
        normalized = normalized.replaceAll("\\s+", " ").trim();
        
        return normalized;
    }
    
    /**
     * Calcula una puntuación de similitud compuesta usando varios algoritmos
     */
    private double calculateCompositeSimilarity(String s1, String s2) {
        // Jaro-Winkler es bueno para comparar nombres cortos (da más peso al principio de la cadena)
        double jaroScore = jaroWinkler.apply(s1, s2);
        
        // Distancia de Levenshtein normalizada
        int distance = levenshtein.apply(s1, s2);
        double maxLength = Math.max(s1.length(), s2.length());
        double levenScore = maxLength > 0 ? 1.0 - (distance / maxLength) : 0;
        
        // Verificar la presencia de palabras clave comunes
        double commonWordsScore = calculateCommonWordsScore(s1, s2);
        
        // Combinamos las puntuaciones (podemos ajustar los pesos según sea necesario)
        // Damos más peso a Jaro-Winkler porque es especialmente bueno para nombres de productos
        return (jaroScore * 0.6) + (levenScore * 0.3) + (commonWordsScore * 0.1);
    }
    
    /**
     * Calcula una puntuación basada en palabras comunes entre dos textos
     */
    private double calculateCommonWordsScore(String s1, String s2) {
        // Dividir en palabras
        Set<String> words1 = new HashSet<>(Arrays.asList(s1.split("\\s+")));
        Set<String> words2 = new HashSet<>(Arrays.asList(s2.split("\\s+")));
        
        // Eliminar palabras vacías
        words1.removeIf(String::isEmpty);
        words2.removeIf(String::isEmpty);
        
        if (words1.isEmpty() || words2.isEmpty()) {
            return 0;
        }
        
        // Calcular la intersección y unión
        Set<String> intersection = new HashSet<>(words1);
        intersection.retainAll(words2);
        
        Set<String> union = new HashSet<>(words1);
        union.addAll(words2);
        
        // Índice Jaccard: tamaño de la intersección dividido por tamaño de la unión
        return (double) intersection.size() / union.size();
    }
    
    /**
     * Calcula un umbral dinámico basado en la longitud y características del texto
     */
    private double calculateDynamicThreshold(String text) {
        double threshold = BASE_SIMILARITY_THRESHOLD;
        
        // Para textos muy cortos, incrementamos el umbral
        if (text.length() < 5) {
            threshold += 0.15;
        }
        // Para textos largos, podemos ser más flexibles
        else if (text.length() > 15) {
            threshold -= 0.05;
        }
        
        // Si el texto tiene muchas palabras, podemos ser más flexibles
        int wordCount = text.split("\\s+").length;
        if (wordCount > 3) {
            threshold -= 0.05;
        }
        
        // Limitamos el umbral final entre 0.7 y 0.9
        return Math.min(0.9, Math.max(0.7, threshold));
    }
    
    /**
     * Clase para almacenar el resultado de una coincidencia con su puntuación
     */
    private static class MatchResult {
        private final Products product;
        private final double score;
        
        public MatchResult(Products product, double score) {
            this.product = product;
            this.score = score;
        }
        
        public Products getProduct() {
            return product;
        }
        
        public double getScore() {
            return score;
        }
    }
}