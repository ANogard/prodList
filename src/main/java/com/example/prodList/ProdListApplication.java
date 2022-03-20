package com.example.prodList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProdListApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProdListApplication.class, args);
	}

}
