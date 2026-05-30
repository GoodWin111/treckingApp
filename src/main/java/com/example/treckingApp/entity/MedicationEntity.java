package com.example.treckingApp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.ArrayList;

@Setter
@Getter
@Entity
@Table(name = "medications")
public class MedicationEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotEmpty
    private UserEntity user;

    @Size(max = 100)
    @NotBlank(message = "поле 'тип' не заполнено")
    private String type;

    @Size(max = 100)
    @NotNull(message = "поле 'название' не заполнено")
    private String name;

    @Min(value = 1, message = "некорректное количество дней")
    private int totalDays;

    @Min(value = 1, message = "некорректное количество приемов в день")
    private int timesPerDay;
}
