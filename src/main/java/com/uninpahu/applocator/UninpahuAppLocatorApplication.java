package com.uninpahu.applocator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class UninpahuAppLocatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(UninpahuAppLocatorApplication.class, args);
	}

}
