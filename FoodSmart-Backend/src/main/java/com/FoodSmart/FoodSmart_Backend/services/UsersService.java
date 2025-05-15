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
    
    /**
     * Autenticar un usuario por correo y contraseña
     * @param mail Correo del usuario
     * @param password Contraseña del usuario
     * @return Usuario autenticado o null si no se encuentra o la contraseña es incorrecta
     */
    public Users authenticate(String mail, String password) {
        List<Users> users = usersRepository.findAll();
        
        // Buscar un usuario con el correo y contraseña proporcionados
        return users.stream()
            .filter(user -> user.getMail().equals(mail) && user.getPassword().equals(password))
            .findFirst()
            .orElse(null);
    }
}