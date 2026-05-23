package com.example.treckingApp.controllers;

import com.example.treckingApp.dto.AuthDTO;
import com.example.treckingApp.dto.UserDTO;
import com.example.treckingApp.entity.UserEntity;
import com.example.treckingApp.security.JWTUtil;
import com.example.treckingApp.security.PersonDetails;
import com.example.treckingApp.services.RegistrationService;
import com.example.treckingApp.util.PersonValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/app")
public class AppController {
    private final PersonValidator personValidator;
    private final RegistrationService registrationService;

    private final JWTUtil jwtUtil;
    private final ModelMapper modelMapper;

    private final AuthenticationManager authenticationManager;

    public AppController(PersonValidator personValidator, RegistrationService registrationService, JWTUtil jwtUtil, ModelMapper modelMapper, AuthenticationManager authenticationManager) {
        this.personValidator = personValidator;
        this.registrationService = registrationService;
        this.jwtUtil = jwtUtil;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
    }

    public UserEntity convertToUser(UserDTO userDTO) {      // конвертируем DTO в UserEntity
        return this.modelMapper.map(userDTO, UserEntity.class);
    }

    @GetMapping("/showUserInfo")
    public String showUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        System.out.println(personDetails.getUser());

        return personDetails.getUsername();
    }

    @PostMapping("/registration")
    public Map<String, String> performRegistration(@RequestBody @Valid UserDTO userDTO,
                                   BindingResult bindingResult) {
        UserEntity userEntity = convertToUser(userDTO);
        personValidator.validate(userEntity, bindingResult);

        if (bindingResult.hasErrors()) return Map.of("message", "Ошибка регистрации");

        registrationService.register(userEntity);

        String token = jwtUtil.generateToken(userEntity.getUsername());
        return Map.of("jwt-token", token);
    }

    @PostMapping("/login")
    public Map<String, String> loginPage(@RequestBody AuthDTO authDTO) {     // для обновления токена
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authDTO.getUsername(),
                        authDTO.getPassword());     // Далее создаём токен для аутентификации из полученных данных
        try {   // проверка логина и пароля через БД (Spring делает это сам)
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            return Map.of("message", "Incorrect credentials");
        }
        String token = jwtUtil.generateToken(authDTO.getUsername());
        return Map.of("jwt-token", token);

    }
}
