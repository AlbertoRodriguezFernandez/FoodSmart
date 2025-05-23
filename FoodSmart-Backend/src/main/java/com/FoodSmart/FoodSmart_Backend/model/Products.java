package com.FoodSmart.FoodSmart_Backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true) //Ignorar campos desconocidos en el JSON
public class Products {

    private String id;

    @JsonProperty("price") //Para mapear el campo "price" del JSON a la propiedad "price" de la clase
    private String price;

    @JsonProperty("product_name")
    private String product_name;

    @JsonProperty("image_url")
    private String image_url;
    
    // Campos de información nutricional de OpenFoodFacts
    @JsonProperty("nutriscore_grade")
    private String nutriscoreGrade;
    
    @JsonProperty("ingredients_text")
    private String ingredientsText;
    
    @JsonProperty("brands")
    private String brands;
    
    @JsonProperty("quantity")
    private String quantity;
    
    @JsonProperty("nutriments")
    private Map<String, Object> nutriments;
    
    // Campos de categorías
    @JsonProperty("categories")
    private String categories;
    
    @JsonProperty("category_tags")
    private List<String> categoryTags;
    
    // Campos de integración
    @JsonProperty("source")
    private String source;
    
    @JsonProperty("match_confidence")
    private Double matchConfidence;

    // Constructor vacío
    public Products() {}
    
    // Constructores con parámetros
    public Products(String productName, String imageUrl, String price) {
        this.product_name = productName;
        this.image_url = imageUrl;
        this.price = price;
    }
    
    public Products(String productName, String imageUrl) {
        this.product_name = productName;
        this.image_url = imageUrl;
    }
    
    // Getters y setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getProductName() { return product_name; }
    public void setProductName(String productName) { this.product_name = productName; }
    
    public String getImageUrl() { return image_url; }
    public void setImageUrl(String imageUrl) { this.image_url = imageUrl; }
    
    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }
    
    public String getNutriscoreGrade() { return nutriscoreGrade; }
    public void setNutriscoreGrade(String nutriscoreGrade) { this.nutriscoreGrade = nutriscoreGrade; }
    
    public String getIngredientsText() { return ingredientsText; }
    public void setIngredientsText(String ingredientsText) { this.ingredientsText = ingredientsText; }
    
    public String getBrands() { return brands; }
    public void setBrands(String brands) { this.brands = brands; }
    
    public String getQuantity() { return quantity; }
    public void setQuantity(String quantity) { this.quantity = quantity; }
    
    public Map<String, Object> getNutriments() { return nutriments; }
    public void setNutriments(Map<String, Object> nutriments) { this.nutriments = nutriments; }
    
    public String getCategories() { return categories; }
    public void setCategories(String categories) { this.categories = categories; }
    
    public List<String> getCategoryTags() { return categoryTags; }
    public void setCategoryTags(List<String> categoryTags) { this.categoryTags = categoryTags; }
    
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    
    public Double getMatchConfidence() { return matchConfidence; }
    public void setMatchConfidence(Double matchConfidence) { this.matchConfidence = matchConfidence; }
}
//Define la estructura de datos que se maneja y devuelve al frontend. 
//JsonIgnoreProperties se usa para ignorar campos adicionales en el JSON recibido. 
//JsonProperty mapea los nombres de campos en formato snake_case del JSON a las propiedades Java
//Los getters y setters permiten acceder y modificar los atributos de la clase. Son utilizados automáticamente 
//por el framework para serializar y deserializar objetos JSON.