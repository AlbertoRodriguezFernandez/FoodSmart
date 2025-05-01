export interface ProductsModel {
    //product_id: number; //REVISAR
    //product_name: string;
    /*category: string;
    price: number;
    nutriscore: CharacterData;
    quantity: number;
    allergens: Alergeno[];
    nutritional_information: NutritionalInformation[];*/
    //image_url: string;
}

/*export enum Alergeno{
    GLUTEN = "gluten",
    CRUSTACEOS = "crustaceos",
    HUEVO = "huevo",
    PESCADO = "pescado",
    CACAHUETE = "cacahuete",
    SOJA = "soja",
    LECHE = "leche",
    FRUTOS_SECOS = "frutos_secos",
    APIO = "apio",
    MOSTAZA = "mostaza",
    SESAMO = "sesamo",
    SULFITOS = "sulfitos",
    ALTRAMUCES = "altramuces",
    MOLUSCOS = "moluscos"
}

export enum NutritionalInformation{
    ENERGIA = "Energia",
    GRASAS = "Grasas",
    GRASAS_SATURADAS = "Grasas saturadas",
    CARBOHIDRATOS = "Carbohidratos",
    AZUCARES = "Azúcares",
    FIBRA = "Fibra",
    PROTEINAS = "Proteínas",
    SAL = "Sal",
}*/


export interface ProductsModel {
    product_name: string; // Cambiado de product_name
    image_url: string;    // Cambiado de image_url
    // Otros campos...
  }
