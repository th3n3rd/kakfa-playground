package com.example.kafka.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.FullyQualifiedAnnotationBeanNameGenerator;

@SpringBootApplication(
	// Needed in order to disambiguate same beans names across multiple modules (e.g. same repository names)
	nameGenerator = FullyQualifiedAnnotationBeanNameGenerator.class
)
public class EcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}

}
