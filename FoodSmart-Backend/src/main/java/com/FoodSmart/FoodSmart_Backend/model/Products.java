package com.FoodSmart.FoodSmart_Backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Products {

    private String id;

    @JsonProperty("product_name")
    private String product_name;

    @JsonProperty("image_url")
    private String image_url;

    public Products() {} // Constructor vacío requerido para deserialización
    
    public Products(String productName, String imageUrl){
        this.product_name = productName;
        this.image_url = imageUrl;
    }
    
    // Getters y setters
    public String getId(){return id;}
    public void setId(String id){this.id = id;}

    public String getProductName() {return product_name;}
    public void setProductName(String productName){this.product_name = productName;}

    public String getImageUrl() {return image_url;}
    public void setImageUrl(String imageUrl){this.image_url = imageUrl;}
}

//Define la estructura de datos que se maneja y devuelve al frontend. 
//JsonIgnoreProperties se usa para ignorar campos adicionales en el JSON recibido. 
//JsonProperty mapea los nombres de campos en formato snake_case del JSON a las propiedades Java
//Los getters y setters permiten acceder y modificar los atributos de la clase. Son utilizados automáticamente 
//por el framework para serializar y deserializar objetos JSON.