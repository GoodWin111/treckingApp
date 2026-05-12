package com.example.treckingApp.config;

import com.example.treckingApp.services.PersonDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration      // помечает файл как конфигурационный
@EnableWebSecurity  // для включения в веб приложения
public class SpringConfig {

    @Bean   // Используется когда метод, например, возвращает класс из библиотеки
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form


        return http.build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {       // здесь прописывается, как именно нужно проверять пароли
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
