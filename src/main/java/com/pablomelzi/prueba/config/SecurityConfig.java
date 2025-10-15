package com.pablomelzi.prueba.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()         // Desactiva CSRF (para pruebas o APIs sin login)
                .authorizeHttpRequests(authz -> authz
                        .anyRequest().permitAll()  // Permitir todas las peticiones sin autenticación
                );
        return http.build();
    }
}
