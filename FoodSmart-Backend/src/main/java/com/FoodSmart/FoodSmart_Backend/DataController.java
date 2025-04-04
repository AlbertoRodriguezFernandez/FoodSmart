package com.FoodSmart.FoodSmart_Backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {

    @GetMapping("/api/saludo")
    public String saludo() {
        return "¡Hola desde Spring Boot!";
    }
}