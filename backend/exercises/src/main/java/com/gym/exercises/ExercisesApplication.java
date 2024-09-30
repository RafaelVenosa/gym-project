package com.gym.exercises;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ExercisesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExercisesApplication.class, args);

	}

}
