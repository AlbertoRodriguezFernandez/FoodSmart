package com.FoodSmart.FoodSmart_Backend.repository;

import com.FoodSmart.FoodSmart_Backend.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsersRepository extends JpaRepository<Users, Long> {
}