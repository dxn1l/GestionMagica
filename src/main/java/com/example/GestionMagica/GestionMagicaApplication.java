package com.example.GestionMagica;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GestionMagicaApplication {

	public static void main(String[] args) {

		SpringApplication.run(GestionMagicaApplication.class, args);
	}

	@Bean
	public CommandLineRunner printLoginUrl() {
		return args -> {
			System.out.println("Enlace a la página de inicio de sesión: http://localhost:8080/login.html");
		};
	}

}
