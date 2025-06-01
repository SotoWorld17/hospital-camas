package com.ticarum.hospital_camas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.*;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Hospital Camas API", version = "1.0.0", description = "API REST para gestionar camas hospitalarias", contact = @Contact(name = "María Soto Alcázar", email = "mariasoto130302@hotmail.com")))
public class HospitalCamasApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospitalCamasApplication.class, args);
	}

}