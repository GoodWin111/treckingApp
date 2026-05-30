package com.example.treckingApp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {
    @NotEmpty
    @Size(min = 2, max = 100)
    private String username;

    @Min(value = 1900)
    @Column(name = "year_of_birth")
    private int yearOfBirth;

    @NotEmpty
    @Size(min = 8, max = 200)
    private String password;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "refresh_token_expiration")
    private LocalDateTime refreshTokenExpiration;

    @Override
    public String toString() {
        return  "; name: " + username +
                "; password: " + password;
    }
}
