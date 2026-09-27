package com.example.treckingApp.controllers;

import com.example.treckingApp.dto.MedicationDTO;
import com.example.treckingApp.entity.IntakeTimeEntity;
import com.example.treckingApp.entity.MedicationEntity;
import com.example.treckingApp.entity.UserEntity;
import com.example.treckingApp.repository.IntakeTimeRepository;
import com.example.treckingApp.repository.MedicationRepository;
import com.example.treckingApp.repository.UserRepository;
import com.example.treckingApp.services.PersonDetailsService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/app")
public class AppController {
    private final PersonDetailsService personDetailsService;
    private final MedicationRepository medicationRepository;
    private final IntakeTimeRepository intakeTimeRepository;
    private final ModelMapper modelMapper;

    public AppController(UserRepository userRepository, PersonDetailsService personDetailsService, MedicationRepository medicationRepository, IntakeTimeRepository intakeTimeRepository, ModelMapper modelMapper) {
        this.personDetailsService = personDetailsService;
        this.medicationRepository = medicationRepository;
        this.intakeTimeRepository = intakeTimeRepository;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/addMedication")
    public ResponseEntity<String> addMedication(@RequestBody @Valid MedicationDTO medicationDTO) {
        MedicationEntity medicationEntity = modelMapper.map(medicationDTO, MedicationEntity.class);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntity user = personDetailsService.findByUsername(username);
        medicationEntity.setUser(user);

        medicationRepository.save(medicationEntity);

        List<LocalTime> dailyIntakesTimes = medicationDTO.getDailyIntakesTimes();
        int totalDays = medicationDTO.getTotalDays();


        for (int i = 0; i < totalDays; i++) {
            IntakeTimeEntity intakeTimeEntity = new IntakeTimeEntity();
            intakeTimeEntity.setMedication(medicationEntity);
            intakeTimeEntity.setNumberOfIntake((Integer) i + 1);
            intakeTimeEntity.setIntakeTime(dailyIntakesTimes.get(i % 3));
            intakeTimeRepository.save(intakeTimeEntity);
        }

        return ResponseEntity.ok("Препарат успешно добавлен: " + medicationEntity.getName());
    }
}
