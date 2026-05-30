package com.example.treckingApp.controllers;

import com.example.treckingApp.dto.AuthDTO;
import com.example.treckingApp.dto.UserDTO;
import com.example.treckingApp.entity.UserEntity;
import com.example.treckingApp.security.JWTUtil;
import com.example.treckingApp.services.RegistrationService;
import com.example.treckingApp.util.PersonValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final PersonValidator personValidator;
    private final RegistrationService registrationService;

    private final JWTUtil jwtUtil;
    private final ModelMapper modelMapper;

    private final AuthenticationManager authenticationManager;

    public AuthController(PersonValidator personValidator, RegistrationService registrationService, JWTUtil jwtUtil, ModelMapper modelMapper, AuthenticationManager authenticationManager) {
        this.personValidator = personValidator;
        this.registrationService = registrationService;
        this.jwtUtil = jwtUtil;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
    }

    public UserEntity convertToUser(UserDTO userDTO) {      // конвертируем DTO в UserEntity
        return this.modelMapper.map(userDTO, UserEntity.class);
    }

    @PostMapping("/registration")
    public Map<String, String> performRegistration(@RequestBody @Valid UserDTO userDTO,
                                   BindingResult bindingResult) {
        UserEntity userEntity = convertToUser(userDTO);
        personValidator.validate(userEntity, bindingResult);

        if (bindingResult.hasErrors()) return Map.of("message", "Ошибка регистрации");

        return registrationService.register(userEntity);
    }

    @PostMapping("/login")
    public Map<String, String> loginPage(@RequestBody AuthDTO authDTO) {     // для обновления токена
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authDTO.getUsername(), authDTO.getPassword());     // Далее создаём токен для аутентификации из полученных данных
        try {   // проверка логина и пароля через БД (Spring делает это сам)
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            return Map.of("message", "Incorrect credentials");
        }

        return registrationService.authenticate(authDTO.getUsername());

    }

    @PostMapping("/refresh")
    public Map<String, String> refresh(@RequestBody Map<String, String> body) {
        String refreshToken = body.get("refreshToken");

        if (refreshToken == null) return Map.of("message", "Missing refresh token");

        return registrationService.refreshToken(refreshToken);
    }
}
