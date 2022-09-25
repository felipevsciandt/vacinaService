package com.felipe.arqsoftware.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
//@EnableFeignClients
public class BancarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(BancarioApplication.class, args);

	}

}
