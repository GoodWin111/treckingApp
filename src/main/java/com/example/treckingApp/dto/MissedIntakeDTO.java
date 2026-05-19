package com.example.treckingApp.dto;

import com.example.treckingApp.entity.MedicationEntity;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
public class MissedIntakeDTO {
    @ManyToOne
    @JoinColumn(name = "medication_id")
    private MedicationEntity medication;

    private int numberDay;

    @CreationTimestamp
    @Column(name = "dateTime", updatable = false)
    private LocalDateTime dateTime;
}
