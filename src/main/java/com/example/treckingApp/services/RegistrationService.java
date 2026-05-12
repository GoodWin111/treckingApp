package com.example.treckingApp.services;

import com.example.treckingApp.entity.UserEntity;
import com.example.treckingApp.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {
    private final UserRepository userRepository;

    public RegistrationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void register(UserEntity user) {
        userRepository.save(user);
    }
}
