package com.gym.authuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AuthuserApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthuserApplication.class, args);
	}

}
