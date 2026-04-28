package com.example.treckingApp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


import java.util.ArrayList;

@Setter
@Getter
@Entity
@Table(name = "medications")
public class MedicationEntity extends BaseEntity {
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
