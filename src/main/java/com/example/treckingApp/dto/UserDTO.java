package com.example.treckingApp.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    @NotEmpty
    @Size(min = 2, max = 100)
    private String username;

    @NotEmpty
    @Min(value = 1900)
    @Column(name = "year_of_birth")
    private int yearOfBirth;

    @NotEmpty
    private String password;
}
