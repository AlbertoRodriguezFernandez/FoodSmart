// CLASE PRINCIPAL DEL PROYECTO SPRING BOOT
// Esta clase es la entrada principal de la aplicación Spring Boot.
// Se encarga de iniciar el contexto de la aplicación y cargar la configuración necesaria.

package com.FoodSmart.FoodSmart_Backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FoodSmartBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodSmartBackendApplication.class, args);
		System.out.println("hola mundillo");
		System.out.println("hola mundillo desde GIT");
	}

}


