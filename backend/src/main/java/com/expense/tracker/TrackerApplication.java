package com.expense.tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.SecureRandom;

@SpringBootApplication
public class TrackerApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(TrackerApplication.class, args);
	}

}
