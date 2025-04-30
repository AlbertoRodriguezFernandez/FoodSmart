// Clase que representa la entidad de la BD
// Esta clase se utiliza para mapear la tabla "users" de la base de datos a un objeto Java.

package com.FoodSmart.FoodSmart_Backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users") // Asegúrate de que coincide con el nombre en MySQL
public class Users {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Puedes usar otra clave primaria si es necesario
    private String name;
    private String mail;
    private String password;

    public Users() {}

    public Users(String mail, String name, String password) {
        this.mail = mail;
        this.name = name;
        this.password = password;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMail() { return mail; }
    public void setMail(String mail) { this.mail = mail; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}