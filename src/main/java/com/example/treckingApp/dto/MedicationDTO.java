package com.example.treckingApp.dto;

import com.example.treckingApp.entity.UserEntity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class MedicationDTO {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @NotEmpty
    @Size(max = 100)
    private String type;

    @NotEmpty
    @Size(max = 100)
    private String name;

    private int totalDays;
    private int timesPerDay;
    private ArrayList<String> timeIntake;
}
