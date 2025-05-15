package com.FoodSmart.FoodSmart_Backend.controllers;

import java.io.IOException;
import java.util.List;

import com.FoodSmart.FoodSmart_Backend.model.Products;
import com.FoodSmart.FoodSmart_Backend.services.ProductIntegrationService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/integrated")
@CrossOrigin
public class IntegratedProductController {

    private final ProductIntegrationService integrationService;

    public IntegratedProductController(ProductIntegrationService integrationService) {
        this.integrationService = integrationService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<Products>> searchIntegratedProducts(@RequestParam String q) {
        try {
            List<Products> products = integrationService.getIntegratedProducts(q);
            return ResponseEntity.ok(products);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}