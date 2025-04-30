// Clases que implementan la lógica de negocio
// Esta clase se encarga de manejar la lógica de negocio del sistema.


package com.FoodSmart.FoodSmart_Backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import com.FoodSmart.FoodSmart_Backend.model.Users;
import com.FoodSmart.FoodSmart_Backend.repository.UsersRepository;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    public List<Users> getUsers() {
        return usersRepository.findAll();
    }

    public Optional<Users> getUserById(Long id) {
        return usersRepository.findById(id);
    }

    public Users saveUser(Users usuario) {
        return usersRepository.save(usuario);
    }

    public void deleteUser(Long id) {
        usersRepository.deleteById(id);
    }
}
