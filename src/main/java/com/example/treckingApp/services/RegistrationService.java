package com.example.treckingApp.services;

import com.example.treckingApp.entity.UserEntity;
import com.example.treckingApp.repository.UserRepository;
import com.example.treckingApp.security.JWTUtil;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class RegistrationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    public RegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JWTUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public Map<String, String> register(UserEntity user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());

        String accessToken = jwtUtil.generateToken(user.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUsername());

        user.setPassword(encodedPassword);

        user.setRefreshToken(refreshToken);
        user.setRefreshTokenExpiration(LocalDateTime.now().plusDays(60));

        userRepository.save(user);

        return Map.of("accessToken", accessToken,
                "refreshToken", refreshToken);
    }

    @Transactional
    public Map<String, String> refreshToken(String oldRefreshToken) {
        UserEntity user = userRepository.findByRefreshToken(oldRefreshToken)
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));

        if (user.getRefreshTokenExpiration().isBefore(LocalDateTime.now()))     // проверяем не истёк ли токен
            throw new RuntimeException("Refresh token expired");

        String newAccessToken = jwtUtil.generateToken(user.getUsername());
        String newRefreshToken = jwtUtil.generateRefreshToken(user.getUsername());

        user.setRefreshToken(newRefreshToken);
        user.setRefreshTokenExpiration(LocalDateTime.now().plusDays(60));

        return Map.of("accessToken", newAccessToken,
                "refreshToken", newRefreshToken);
    }

    @Transactional
    public Map<String, String> authenticate(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String accessToken = jwtUtil.generateToken(username);
        String refreshToken = jwtUtil.generateRefreshToken(username);

        user.setRefreshToken(refreshToken);
        user.setRefreshTokenExpiration(LocalDateTime.now().plusDays(60));

        return Map.of("accessToken", accessToken,
                "refreshToken", refreshToken);
    }
}
