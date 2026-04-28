package com.example.treckingApp.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
public class MedicationDTO {
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
