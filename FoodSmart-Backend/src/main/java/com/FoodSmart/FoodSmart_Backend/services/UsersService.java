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

    public List<Users> obtenerUsuarios() {
        return usersRepository.findAll();
    }

    public Optional<Users> obtenerUsuarioPorId(Long id) {
        return usersRepository.findById(id);
    }

    public Users guardarUsuario(Users usuario) {
        return usersRepository.save(usuario);
    }

    public void eliminarUsuario(Long id) {
        usersRepository.deleteById(id);
    }
}