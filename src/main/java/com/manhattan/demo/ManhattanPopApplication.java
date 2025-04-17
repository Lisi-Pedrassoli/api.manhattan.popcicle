package com.manhattan.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//Define a classe princial da aplicação Spring boot
@SpringBootApplication
public class ManhattanPopApplication {

	public static void main(String[] args) {
		// Inicializa e executa a aplicação Spring Boot
		// Configura o contexto do Spring e inicia o servidor embutido
		SpringApplication.run(ManhattanPopApplication.class, args);
	}
}