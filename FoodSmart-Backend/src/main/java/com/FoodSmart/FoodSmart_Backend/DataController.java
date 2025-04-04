package com.FoodSmart.FoodSmart_Backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class DataController {
    @GetMapping("/api/saludo")
    public String saludo() {
        return "¡Hola desde el backend!";
    }
}