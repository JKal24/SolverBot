package com.astro.smitesolver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SmiteSolverApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmiteSolverApplication.class, args);
	}

}
