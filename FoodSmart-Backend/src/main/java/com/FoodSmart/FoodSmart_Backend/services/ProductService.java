package com.FoodSmart.FoodSmart_Backend.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.FoodSmart.FoodSmart_Backend.model.Products;

import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    private final RestTemplate restTemplate = new RestTemplate();

    public List<Products> searchProducts(String query) {
        String url = UriComponentsBuilder
            .fromUriString("https://world.openfoodfacts.org/cgi/search.pl")
            .queryParam("search_terms", query)
            .queryParam("json", "true")
            .toUriString();
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        List<Map<String, Object>> rawProducts = (List<Map<String, Object>>) response.get("products");

        return rawProducts.stream()
                .map(p -> {
                    String name = (String) p.get("product_name");
                    String image = (String) p.get("image_url");
                    return new Products(name, image);
                })
                .toList();
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
