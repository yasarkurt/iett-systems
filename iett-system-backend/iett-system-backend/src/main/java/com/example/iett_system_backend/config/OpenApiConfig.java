package com.example.iett_system_backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {


    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("IETT Bus and Garage API")
                        .version("1.0")
                        .description("API for managing IETT bus and garage data")
                        .contact(new Contact()
                                .name("IETT Support")
                                .email("support@iett.gov.tr")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Development Server")
                ));
    }
}