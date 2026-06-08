package com.example.treckingApp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Setter
@Getter
@Entity
@Table(name = "missed_intakes")
public class MissedIntakeEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "medication_id")
    private MedicationEntity medication;

    private int numberDay;

    @Min(value = 1)
    private Integer numberOfMissedIntake;

    private LocalTime missedIntakeTime;
}
