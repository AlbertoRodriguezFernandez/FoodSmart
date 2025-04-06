// Responsable de manejar solicitudes HTTP del frontend relacionadas con los datos generales del sistema.
// En este caso, se utiliza para manejar una solicitud GET en la ruta "/api/saludo".
// Aquí se podrían implementar endpoints para consumir APIS externas o para Web Scraping.

package com.FoodSmart.FoodSmart_Backend.controllers;

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