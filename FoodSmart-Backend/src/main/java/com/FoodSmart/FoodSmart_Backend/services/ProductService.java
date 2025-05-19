package com.FoodSmart.FoodSmart_Backend.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.FoodSmart.FoodSmart_Backend.model.Products;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.logging.Logger;

@Service
public class ProductService {

    private static final Logger logger = Logger.getLogger(ProductService.class.getName());
    private final RestTemplate restTemplate = new RestTemplate();

    public List<Products> searchProducts(String query) {
        // Construir URL con más parámetros para obtener datos más completos
        String url = UriComponentsBuilder
            .fromUriString("https://world.openfoodfacts.org/cgi/search.pl")
            .queryParam("search_terms", query)
            .queryParam("json", "true")
            .queryParam("page_size", "24")
            .queryParam("fields", "product_name,image_url,price,nutriments,nutrition_grades,nutriscore_grade,brands,quantity,ingredients_text,categories,category_tags")
            .toUriString();
            
        Map<String, Object> response;
        try {
            response = restTemplate.getForObject(url, Map.class);
        } catch (Exception e) {
            logger.warning("Error al conectar con OpenFoodFacts: " + e.getMessage());
            return new ArrayList<>();
        }

        List<Map<String, Object>> rawProducts = (List<Map<String, Object>>) response.get("products");
        List<Products> productsList = new ArrayList<>();

        for (Map<String, Object> p : rawProducts) {
            Products product = new Products();
            
            // Información básica
            product.setProductName((String) p.get("product_name"));
            product.setImageUrl((String) p.get("image_url"));
            product.setSource("openFoodFacts");
            
            // Información nutricional
            product.setNutriscoreGrade((String) p.get("nutriscore_grade"));
            product.setIngredientsText((String) p.get("ingredients_text"));
            product.setBrands((String) p.get("brands"));
            product.setQuantity((String) p.get("quantity"));
            
            // Categorías
            product.setCategories((String) p.get("categories"));
            product.setCategoryTags((List<String>) p.get("category_tags"));
            
            // Nutrientes (si están disponibles)
            if (p.containsKey("nutriments")) {
                product.setNutriments((Map<String, Object>) p.get("nutriments"));
            }
            
            productsList.add(product);
        }
        
        return productsList;
    }
}

//Contiene la lógica de negocio para buscar productos. 
//Usa restTemplate para hacer peticiones HTTP a la API de Open Food Facts.
//1. Construye la URL de la API con los parámetros de búsqueda.
//2. Realiza la petición GET a la API.
/*Procesa la respuesta JSON de la API en varios pasos: 
1. Recibe la respuesta como un mapa de clave-valor.(map genérico)
2. Extrae la lista de productos de la respuesta (rawProducts).
3. Convierte cada producto en un objeto Products usando stream().map() y devuelve una lista de objetos Products.
4. Cada objeto Products contiene el nombre y la URL de la imagen del producto. (los que necesitemos)
*/  
//No se almacenan los datos persistentemente(no hay base de datos involucrada).Los datos tan solo existen en memoria durante la petición. 
