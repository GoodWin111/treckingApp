package com.example.treckingApp.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthDTO {      // в этом DTO будут посылаться данные для аутентификации
    @NotEmpty
    @Size(min = 2, max = 100)
    private String username;
    private String password;
}
