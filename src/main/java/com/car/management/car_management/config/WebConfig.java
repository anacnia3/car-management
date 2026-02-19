package com.car.management.car_management.config;

import net.kaczmarzyk.spring.data.jpa.web.SpecificationArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configuração para permitir filtros avançados na API (Specification)
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new SpecificationArgumentResolver());
    }

    /**
     * Configuração de CORS para liberar o acesso do Frontend (Next.js)
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Libera todos os endpoints: /auth/login, /auth/register, /cars
                .allowedOrigins("http://localhost:3000") // URL exata do seu frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Permite o clique de salvar e criar
                .allowedHeaders("*") // Permite o envio do Token JWT nos headers
                .allowCredentials(true)
                .maxAge(3600); // Cache da permissão por 1 hora
    }
}