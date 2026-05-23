package com.example.treckingApp.config;

import com.example.treckingApp.security.JWTUtil;
import com.example.treckingApp.services.PersonDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;
    private final PersonDetailsService personDetailsService;

    public JWTFilter(JWTUtil jwtUtil, PersonDetailsService personDetailsService) {
        this.jwtUtil = jwtUtil;
        this.personDetailsService = personDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {   // фильтр отлавливает запросы
        String authHeader = request.getHeader("Authorization");     // получаем доступ к http запросу и заголовку "Authorization"

        if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer")) {
            String jwt = authHeader.substring(7); // получаем сам токен

            if (jwt.isBlank()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT-token in Bearer Header");
            } else {
                try {
                    String username = jwtUtil.validateTokenAndRetrieveSubject(jwt); // Получение имени, если токен валиден
                    UserDetails userDetails = personDetailsService.loadUserByUsername(username);    // проверяем есть ли такой человек

                    // добавляем аутентификацию
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null,
                                    userDetails.getAuthorities());

                    if (SecurityContextHolder.getContext().getAuthentication() == null) {   // кладем в контекст (временное хранилище информации о текущем пользователе, который совершает запрос)
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                } catch (Exception e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT-token");
                }
            }
        }

        filterChain.doFilter(request, response);    // вызываем работу фильтров
    }
}
