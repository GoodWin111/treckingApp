package com.example.treckingApp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "missed_intakes")
public class MissedIntakeEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "medication_id")
    private MedicationEntity medication;

    private int numberDay;

    @CreationTimestamp
    @Column(name = "dateTime", updatable = false)
    private LocalDateTime dateTime;
}
