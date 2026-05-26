package com.example.treckingApp.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JWTUtil {
    @Value("${jwt_secret}")
    private String secret;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));    // шифруем секрет
    }

    public String generateRefreshToken(String username) {

        Date expirationDate = (Date) Date.from(ZonedDateTime.now().plusDays(60).toInstant());    // срок жизни токена
        return Jwts.builder()
                .subject(username)  // для кого
                .claim("username", username)    // что хранить в токене
                .issuedAt(new Date())   // текущая дата
                .expiration(expirationDate)
                .signWith(getSigningKey())
                .compact();

        // .issuer()    кто выдал токен
    }

    public String generateToken(String username) {

        Date expirationDate = (Date) Date.from(ZonedDateTime.now().plusMinutes(60).toInstant());    // срок жизни токена
        return Jwts.builder()
                .subject(username)  // для кого
                .claim("username", username)    // что хранить в токене
                .issuedAt(new Date())   // текущая дата
                .expiration(expirationDate)
                .signWith(getSigningKey())
                .compact();     // сборка в строку

        // .issuer()    кто выдал токен
    }

    public String validateTokenAndRetrieveSubject(String token) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        return Jwts.parser()    // парсер должен проверить время и секрет (.requireIssuer() - проверка отправителя)
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
