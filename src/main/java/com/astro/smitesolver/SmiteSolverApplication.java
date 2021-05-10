package com.astro.smitesolver;

import com.astro.smitesolver.discord.service.MatchParserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SmiteSolverApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(SmiteSolverApplication.class, args);
		MatchParserService matchParserService = applicationContext.getBean(MatchParserService.class);
		matchParserService.updateData(2);
	}

}
