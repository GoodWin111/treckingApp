package com.example.treckingApp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "intakes_time")
public class IntakeTimeEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "medication_id")
    private MedicationEntity medication;

    @NotEmpty
    @Min(value = 1)
    private Integer numberOfIntake;

    @NotEmpty
    @CreationTimestamp
    private LocalDateTime IntakeDateTime;
}
