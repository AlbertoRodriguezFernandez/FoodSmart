package com.FoodSmart.FoodSmart_Backend.controllers;

import com.FoodSmart.FoodSmart_Backend.model.Users;
import com.FoodSmart.FoodSmart_Backend.services.UsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Map;       // Añadir esta importación
import java.util.HashMap;   // Añadir esta importación

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UsersService usersService;

    // Endpoint para obtener todos los usuarios
    @GetMapping
    public List<Users> getUsers() {
        return usersService.getUsers();
    }

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

    // Endpoint para actualizar un usuario existente
    @PutMapping("/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable Long id, @RequestBody Users user) {
        Optional <Users> existingUser = usersService.getUserById(id);
        if (existingUser != null) {
            user.setId(id); // Aseguramos que el ID sea correcto
            Users updatedUser = usersService.saveUser(user);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para eliminar un usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (usersService.getUserById(id) != null) {
            usersService.deleteUser(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
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




