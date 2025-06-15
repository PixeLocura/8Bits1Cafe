package com.pixelocura.bitscafe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		System.out.println("DB_URL: " + System.getenv("DB_URL"));
    		System.out.println("DB_USERNAME: " + System.getenv("DB_USERNAME"));
		SpringApplication.run(Application.class, args);
	}

}
