package com.empproject.allocation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EmployeeProjectallocationApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeProjectallocationApplication.class, args);
	}

}
