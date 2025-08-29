package com.example.UberProject_ReviewService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class UberProjectReviewServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UberProjectReviewServiceApplication.class, args);
	}

}
