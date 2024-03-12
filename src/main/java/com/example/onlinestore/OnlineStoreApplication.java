package com.example.onlinestore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class OnlineStoreApplication {

	public static void main(String[] args) {
		System.out.println("시작-----------");
		SpringApplication.run(OnlineStoreApplication.class, args);
	}

}
