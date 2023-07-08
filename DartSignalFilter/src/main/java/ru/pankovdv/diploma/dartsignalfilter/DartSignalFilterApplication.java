package ru.pankovdv.diploma.dartsignalfilter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class DartSignalFilterApplication {

    public static void main(String[] args) {
        SpringApplication.run(DartSignalFilterApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000", "http://localhost")
                        .allowCredentials(true)
                        .allowedMethods("GET", "POST", "UPDATE", "DELETE", "PUT");
            }
        };
    }
}

