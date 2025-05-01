package com.FoodSmart.FoodSmart_Backend.controllers;

import java.util.List;

import com.FoodSmart.FoodSmart_Backend.model.Products;
import com.FoodSmart.FoodSmart_Backend.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:4200")  
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/search")
    public List<Products> searchProducts(@RequestParam("q") String query) {
        return productService.searchProducts(query);
    }
}

//RequestMapping define la URL base para todos los endpoints de este controlador (AFECTA A TODOS SUS MÉTODOS)
//GetMapping define un endpoint específico para manejar peticiones GET en la URL /search
//Con CrossOrigin permitimos el acceso desde el frontend. Es de donde llegan sus peticiones
//Definimos un endpoint REST en /api/products/search?q=termino_busqueda 
//Recibe el parámetro de búsqueda query desde la URL. 
//Directamente delega la petición (búsqueda) al servicio y devuelve la lista de productos obtenida. 