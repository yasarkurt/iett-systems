package com.example.iett_system_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableScheduling // Zamanlanmış görevleri etkinleştir
public class IettSystemBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(IettSystemBackendApplication.class, args);
	}

	/**
	 * CORS yapılandırması - API'ye farklı kaynaklardan erişim için
	 */
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/**") // Tüm API endpointleri için CORS'u etkinleştir
						.allowedOrigins("*") // Geliştirme için tüm kaynaklara izin ver
						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
						.allowedHeaders("*");
			}
		};
	}
}