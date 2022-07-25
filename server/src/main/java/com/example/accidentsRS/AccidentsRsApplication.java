package com.example.accidentsRS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.example.accidentsRS")
public class AccidentsRsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccidentsRsApplication.class, args);
	}

}
