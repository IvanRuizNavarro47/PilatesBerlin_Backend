package com.example.berlinpilatesbackend;

import com.example.berlinpilatesbackend.service.UsuarioService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@SecurityScheme(name = "Bearer Authentication", scheme = "bearer" , type = SecuritySchemeType.HTTP, bearerFormat = "JWT")
@OpenAPIDefinition(info = @Info(title = "BerlinPilates API Documentation" , description = "Documentation Page API" , version = "1.0"))
public class BerlinPilatesBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BerlinPilatesBackendApplication.class, args);
    }

}