// Interfaz que interactúa con la base de datos
// Esta interfaz extiende JpaRepository, lo que proporciona métodos CRUD básicos para la entidad Users.
// No es necesario implementar estos métodos, ya que JpaRepository lo hace automáticamente.

package com.FoodSmart.FoodSmart_Backend.repository;

import com.FoodSmart.FoodSmart_Backend.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    // Método para buscar un usuario por su correo electrónico
    Optional<Users> findByMail(String mail);
}