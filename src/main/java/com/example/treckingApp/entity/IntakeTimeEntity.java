package com.example.treckingApp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "intakes_time")
public class IntakeTimeEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "medication_id")
    private MedicationEntity medication;

    @Min(value = 1)
    private Integer numberOfIntake;

    private LocalTime intakeTime;
}
