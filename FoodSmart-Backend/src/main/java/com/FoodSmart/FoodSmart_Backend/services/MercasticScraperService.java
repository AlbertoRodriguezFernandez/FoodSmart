package com.FoodSmart.FoodSmart_Backend.services;

import org.springframework.stereotype.Service;
import com.FoodSmart.FoodSmart_Backend.model.Products;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Service
public class MercasticScraperService {

    public List<Products> searchProducts(String query) throws IOException {
        List<Products> products = new ArrayList<>();

        // Asegúrate de que esta URL sea correcta para Mercastic
        String url = "https://mercastic.es/search?q=" + URLEncoder.encode(query, StandardCharsets.UTF_8);
        
        try {
            Document doc = Jsoup.connect(url)
                               .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                               .timeout(10000)
                               .get();
                               
            Elements cards = doc.select("a[href^=/product/]");

            for (Element card : cards) {
                String nombre = card.select("div.mx-2.font-bold.text-base.text-sea-600.line-clamp-2").text();
                String precio = card.select("span.font-bold.text-venice-700.text-lg.leading-4").text();
                
                // Asignamos URL de imagen vacía para evitar errores
                Products product = new Products(nombre, "", precio);
                products.add(product);
            }
        } catch (Exception e) {
            System.out.println("Error en el web scraping: " + e.getMessage());
            e.printStackTrace();
        }

        return products;
    }
}