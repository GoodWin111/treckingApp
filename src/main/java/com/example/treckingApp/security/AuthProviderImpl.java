package com.example.treckingApp.security;

import com.example.treckingApp.services.PersonDetailsService;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@AllArgsConstructor
public class AuthProviderImpl implements AuthenticationProvider {
    private final PersonDetailsService personDetailsService;

    @Override
    public @Nullable Authentication authenticate(Authentication authentication) throws AuthenticationException {    // здесь лежит логика аутентификации
        // получаем ник и пароль с формы
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails userDetails = personDetailsService.loadUserByUsername(username);    //  поиск пользователя из формы, получая User Details


        if (!password.equals(userDetails.getPassword()))    // сравниваем пароль с тем, что хранится в userDetails
            throw new BadCredentialsException("Incorrect password");

        return new UsernamePasswordAuthenticationToken(userDetails, password, Collections.emptyList());     // если всё хорошо возвращаем объект Authentication
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
