package com.example.treckingApp.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Setter
@Getter
public class MissedIntakeDTO {
    private int numberDay;

    @CreationTimestamp
    @Column(name = "dateTime", updatable = false)
    private LocalDateTime dateTime;
}
