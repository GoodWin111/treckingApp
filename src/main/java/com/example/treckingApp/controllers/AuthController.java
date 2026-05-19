package com.example.treckingApp.controllers;

import com.example.treckingApp.dto.UserDTO;
import com.example.treckingApp.entity.UserEntity;
import com.example.treckingApp.security.JWTUtil;
import com.example.treckingApp.services.RegistrationService;
import com.example.treckingApp.util.PersonValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final PersonValidator personValidator;
    private final RegistrationService registrationService;

    private final JWTUtil jwtUtil;
    private final ModelMapper modelMapper;

    public AuthController(PersonValidator personValidator, RegistrationService registrationService, JWTUtil jwtUtil, ModelMapper modelMapper) {
        this.personValidator = personValidator;
        this.registrationService = registrationService;
        this.jwtUtil = jwtUtil;
        this.modelMapper = modelMapper;
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

        registrationService.register(userEntity);

        String token = jwtUtil.generateToken(userEntity.getUsername());
        return Map.of("jwt-token", token);
    }
}
