package com.trainday.train;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.cloud.openfeign.EnableFeignClients;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;

@EnableMongoAuditing
@SpringBootApplication
@EnableFeignClients
public class TrainApplication {
	@Value("${spring.data.mongodb.uri}")
	private String mongoUri;

	@PostConstruct
	public void test() {
		System.out.println("MONGO URI = " + mongoUri);
	}

	public static void main(String[] args) {
		SpringApplication.run(TrainApplication.class, args);
	}

}
