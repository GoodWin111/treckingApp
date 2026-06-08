package com.example.treckingApp.dto;

import com.example.treckingApp.entity.UserEntity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class MedicationDTO {
    @NotBlank(message = "неверные данные в поле 'тип'")
    @Size(max = 100)
    private String type;

    @NotBlank(message = "неверные данные в поле 'имя препарата'")
    @Size(max = 100)
    private String name;

    @Min(value = 1, message = "неверные данные в поле 'длительность периода'")
    private Integer totalDays;

    @Min(value = 1, message = "неверные данные в поле 'приёмов в день'")
    private Integer timesPerDay;

    @NotNull(message = "неверные данные в поле 'время приёмов в день'")
    private List<LocalTime> dailyIntakesTimes;
}
