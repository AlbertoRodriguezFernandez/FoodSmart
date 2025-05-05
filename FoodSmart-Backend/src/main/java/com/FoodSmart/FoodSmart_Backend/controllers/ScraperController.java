package com.FoodSmart.FoodSmart_Backend.controllers;

import java.io.IOException;
import java.util.List;

import com.FoodSmart.FoodSmart_Backend.model.Products;
import com.FoodSmart.FoodSmart_Backend.services.MercasticScraperService;
import com.FoodSmart.FoodSmart_Backend.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/scraper")
@CrossOrigin // para permitir peticiones desde el frontend
public class ScraperController {

    private final MercasticScraperService scraperService;

    public ScraperController(MercasticScraperService scraperService) {
        this.scraperService = scraperService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<Products>> search(@RequestParam String q) {
        try {
            List<Products> products = scraperService.searchProducts(q);
            return ResponseEntity.ok(products);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}