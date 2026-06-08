package com.example.treckingApp.repository;

import com.example.treckingApp.entity.IntakeTimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IntakeTimeRepository extends JpaRepository<IntakeTimeEntity, Integer> {
}
