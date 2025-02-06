package com.batchproject.rentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class RentServiceMainApplication {

	public static void main(String[] args) {
		SpringApplication.run(RentServiceMainApplication.class, args);
	}

}
