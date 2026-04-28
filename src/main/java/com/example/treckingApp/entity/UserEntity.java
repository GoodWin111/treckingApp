package com.example.treckingApp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {
    @NotEmpty
    @Size(min = 2, max = 100)
    private String username;

    private String password;
}
