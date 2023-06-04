package ru.vsu.cs.chirk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan("ru.vsu.cs.chirk")
@SpringBootApplication
public class ChirkApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChirkApplication.class, args);
	}



}
