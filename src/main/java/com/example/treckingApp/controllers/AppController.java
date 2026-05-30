package com.example.treckingApp.controllers;

import com.example.treckingApp.dto.MedicationDTO;
import com.example.treckingApp.entity.MedicationEntity;
import com.example.treckingApp.repository.MedicationRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/app")
public class AppController {
    private final MedicationRepository medicationRepository;
    private final ModelMapper modelMapper;

    public AppController(MedicationRepository medicationRepository, ModelMapper modelMapper) {
        this.medicationRepository = medicationRepository;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/addMedication")
    public void addMedication(@RequestBody @Valid MedicationDTO medicationDTO, BindingResult bindingResult) {
        MedicationEntity medicationEntity = modelMapper.map(medicationDTO, MedicationEntity.class);

        medicationRepository.save(medicationEntity);
    }
}
