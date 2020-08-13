package edu.project.englishstoriesbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class EnglishStoriesBotApplication {
	{
		ApiContextInitializer.init();
	}
	public static void main(String[] args) {

		SpringApplication.run(EnglishStoriesBotApplication.class, args);
	}

}
