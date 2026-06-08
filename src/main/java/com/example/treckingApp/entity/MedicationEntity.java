package com.example.treckingApp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "medications")
public class MedicationEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Size(max = 100)
    private String type;

    @Size(max = 100)
    private String name;

    @Min(value = 1)
    private int totalDays;

    @Min(value = 1)
    private int timesPerDay;
}
