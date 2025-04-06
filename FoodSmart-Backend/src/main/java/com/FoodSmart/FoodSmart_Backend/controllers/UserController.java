// Responsable de manejar solicitudes HTTP del frontend relacionadas con los usuarios.
// En este caso, se utiliza para manejar una solicitud GET en la ruta "/api/users".

package com.FoodSmart.FoodSmart_Backend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.FoodSmart.FoodSmart_Backend.model.Users;
import com.FoodSmart.FoodSmart_Backend.repository.UsersRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200") // Para permitir peticiones desde Angular
public class UserController {

    private final UsersRepository usersRepository;

    public UserController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @GetMapping (produces = "application/json")
    public List<Users> getUsers() {
        return usersRepository.findAll();
    }
}