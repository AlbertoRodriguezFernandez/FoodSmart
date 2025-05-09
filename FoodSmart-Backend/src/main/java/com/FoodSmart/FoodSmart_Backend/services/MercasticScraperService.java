package com.FoodSmart.FoodSmart_Backend.services;

import org.springframework.stereotype.Service;
import com.FoodSmart.FoodSmart_Backend.model.Products;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Service
public class MercasticScraperService {

    public List<Products> searchProducts(String query) throws IOException {
        List<Products> products = new ArrayList<>();

        // URL base de Mercastic
        String baseUrl = "https://mercastic.es";
        
        // URL de búsqueda
        String url = baseUrl + "/search?q=" + URLEncoder.encode(query, StandardCharsets.UTF_8);
        
        try {
            Document doc = Jsoup.connect(url)
                               .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                               .timeout(10000)
                               .get();
                               
            Elements cards = doc.select("a[href^=/product/]");
            
            System.out.println("Encontrados " + cards.size() + " productos");

            for (Element card : cards) {
                // Extraer nombre del producto
                String nombre = card.select("div.mx-2.font-bold.text-base.text-sea-600.line-clamp-2").text();
                
                // Extraer precio del producto
                String precio = card.select("span.font-bold.text-venice-700.text-lg.leading-4").text();
                
                // ACTUALIZADO: Usar selector correcto para las imágenes
                String imagenUrl = "";
                // Probar diferentes selectores para encontrar la imagen
                Element imgElement = card.select("img.w-full.h-full.object-contain").first();
                if (imgElement == null) {
                    imgElement = card.select("img.w-full.h-full").first();
                }
                if (imgElement == null) {
                    imgElement = card.select("img").first();
                }
                
                if (imgElement != null) {
                    imagenUrl = imgElement.attr("src");
                    System.out.println("URL original de imagen: " + imagenUrl);
                    
                    // Si la URL es relativa, convertirla a absoluta
                    if (imagenUrl.startsWith("/")) {
                        imagenUrl = baseUrl + imagenUrl;
                    }
                    
                    // ACTUALIZADO: Mejor procesamiento de URLs de Next.js
                    if (imagenUrl.contains("/_next/image") || imagenUrl.contains("_next/image")) {
                        // Extraer el parámetro url= usando expresión regular
                        Pattern pattern = Pattern.compile("url=([^&]+)");
                        Matcher matcher = pattern.matcher(imagenUrl);
                        if (matcher.find()) {
                            String encodedUrl = matcher.group(1);
                            try {
                                imagenUrl = java.net.URLDecoder.decode(encodedUrl, StandardCharsets.UTF_8.toString());
                                // Si la URL decodificada empieza con // añadir https:
                                if (imagenUrl.startsWith("//")) {
                                    imagenUrl = "https:" + imagenUrl;
                                }
                                
                                // Usar directamente la URL de la API de Mercastic
                                if (imagenUrl.contains("/mercastic.es/api/image/")) {
                                    // Esta es ya una URL válida para la imagen
                                    System.out.println("URL final procesada: " + imagenUrl);
                                }
                            } catch (Exception e) {
                                System.out.println("Error decodificando URL: " + e.getMessage());
                            }
                        }
                    }
                }
                
                // Crear objeto producto con todos los datos
                Products product = new Products(nombre, imagenUrl, precio);
                products.add(product);
                
                // Log para depuración
                System.out.println("Producto extraído: " + nombre + " | Precio: " + precio + " | Imagen: " + imagenUrl);
            }
        } catch (Exception e) {
            System.out.println("Error en el web scraping: " + e.getMessage());
            e.printStackTrace();
        }

        return products;
    }
}