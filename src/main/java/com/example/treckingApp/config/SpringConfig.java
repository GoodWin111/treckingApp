package com.example.treckingApp.config;

import com.example.treckingApp.security.AuthProviderImpl;
import com.example.treckingApp.services.PersonDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration      // помечает файл как конфигурационный
@EnableWebSecurity  // для включения в веб приложения
public class SpringConfig {
    private final PersonDetailsService personDetailsService;
    private final AuthProviderImpl authProvider;

    public SpringConfig(PersonDetailsService personDetailsService, AuthProviderImpl authProvider) {
        this.personDetailsService = personDetailsService;
        this.authProvider = authProvider;
    }

    @Bean   // Используется когда метод, например, возвращает класс из библиотеки
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return null;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return authProvider;
    }
}
