package com.manhattan.demo.Infrastructure.WebConfig;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ServletComponentScan
public class WebConfig implements WebMvcConfigurer {
    @Override
    // "/**" significa todos os caminhos disponíveis na aplicação, front e back se comunicam mesmo se estiverem em portas diferentes
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") // URL do frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                .allowedHeaders("*");
    }
}