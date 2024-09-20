package com.imepac.ads.servico_de_notas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServicoDeNotasApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicoDeNotasApplication.class, args);
		System.out.println("Serviço de notas iniciado!");
	}
}
