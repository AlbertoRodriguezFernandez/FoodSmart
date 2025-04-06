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
    
    private String correo;
    private String nombre;
    private String contraseña;

    public Users() {}

    public Users(String correo, String nombre, String contraseña) {
        this.correo = correo;
        this.nombre = nombre;
        this.contraseña = contraseña;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getContraseña() { return contraseña; }
    public void setContraseña(String contraseña) { this.contraseña = contraseña; }
}