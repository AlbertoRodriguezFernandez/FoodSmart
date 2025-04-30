// Responsable de manejar solicitudes HTTP del frontend relacionadas con los usuarios.
// En este caso, se utiliza para manejar una solicitud GET en la ruta "/api/users".

package com.FoodSmart.FoodSmart_Backend.controllers;

//package com.FoodSmart.FoodSmart_Backend.model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.FoodSmart.FoodSmart_Backend.model.Users;
import com.FoodSmart.FoodSmart_Backend.repository.UsersRepository;
import com.FoodSmart.FoodSmart_Backend.services.UsersService;


import org.springframework.ui.Model;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200") // Para permitir peticiones desde Angular
public class UserController {

    @Autowired
    private UsersService usersService;

    @GetMapping
    public String listUsers(Model model){
        model.addAttribute("users", usersService.getUsers());
        return "users-list";
    }

    @GetMapping("/api/new_users")
    public String showFormNewUser(Model model){
        model.addAttribute("user", new Users());
        return "user-form";
    }

    @PostMapping
    public String saveUser(Users user){
        usersService.saveUser(user);
        return "redirect:/api/users";
    }
    
    @GetMapping("/edit/{id}")
    public String showFormEditUser(@PathVariable Long id, Model model){
        model.addAttribute("user", usersService.getUserById(id));
        return "user-form";
    }
}




