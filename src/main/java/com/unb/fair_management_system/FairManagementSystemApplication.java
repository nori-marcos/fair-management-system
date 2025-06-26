package com.unb.fair_management_system;

import com.unb.fair_management_system.starter.config.EnvConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FairManagementSystemApplication {

	public static void main(String[] args) {
		System.setProperty("spring.datasource.url", EnvConfig.get("DB_URL"));
		System.setProperty("spring.datasource.username", EnvConfig.get("DB_USER"));
		System.setProperty("spring.datasource.password", EnvConfig.get("DB_PASSWORD"));
		SpringApplication.run(FairManagementSystemApplication.class, args);
	}

}
