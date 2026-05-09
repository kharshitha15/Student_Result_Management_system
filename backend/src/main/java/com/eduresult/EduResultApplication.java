package com.eduresult;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
		title = "Student Result Management API",
		version = "1.0",
		description = "REST API for managing student results, marks and CGPA"
))
public class EduResultApplication {
    private static final Logger logger = LoggerFactory.getLogger(EduResultApplication.class);

	public static void main(String[] args) {
		try {
			SpringApplication.run(EduResultApplication.class, args);
		} catch (Exception e) {
			logger.error("CRITICAL STARTUP FAILURE: ", e);
			System.err.println("CRITICAL STARTUP FAILURE: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}
}
