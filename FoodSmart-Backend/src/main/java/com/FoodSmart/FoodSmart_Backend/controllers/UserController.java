package com.FoodSmart.FoodSmart_Backend.controllers;

import com.FoodSmart.FoodSmart_Backend.model.Users;
import com.FoodSmart.FoodSmart_Backend.services.UsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Map;       
import java.util.HashMap;   

@RestController
@RequestMapping("/api/users")   //Endpoint base para todos los métodos de este controlador
@CrossOrigin(origins = "http://localhost:4200") //Desde donde recibe las peticiones
public class UserController {

    @Autowired
    private UsersService usersService;

    // Endpoint para obtener un usuario por ID
   @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable Long id) {
        Optional<Users> userOpt = usersService.getUserById(id);
        if (userOpt.isPresent()) {
            return ResponseEntity.ok(userOpt.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // Endpoint para crear un nuevo usuario
    @PostMapping
    public ResponseEntity<Users> createUser(@RequestBody Users user) {
        Users savedUser = usersService.saveUser(user);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String mail = credentials.get("mail");
        String password = credentials.get("password");
        
        if (mail == null || password == null) {
            return ResponseEntity.badRequest().body("Email y contraseña son requeridos");
        }
        
        Users user = usersService.authenticate(mail, password);
        
        if (user != null) {
            // Crear un mapa para devolver solo información relevante (sin contraseña)
            Map<String, Object> response = new HashMap<>();
            response.put("id", user.getId());
            response.put("name", user.getName());
            response.put("mail", user.getMail());
            response.put("message", "Inicio de sesión exitoso");
            
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }

}




