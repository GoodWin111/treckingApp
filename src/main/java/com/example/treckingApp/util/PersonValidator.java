package com.example.treckingApp.util;

import com.example.treckingApp.entity.UserEntity;
import com.example.treckingApp.services.PersonDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {     // здесь реализуем интерфейс валидатора и его 2 метода
    private final PersonDetailsService personDetailsService;

    public PersonValidator(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {   // должен указывать на то, что валидатор требуется для объектов класса UserEntity. Соответственно его и возвращаем
        return UserEntity.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserEntity user = (UserEntity) target;  // переданное значение приводим к типу UserEntity
        try {
            personDetailsService.loadUserByUsername(user.getUsername());    // проверка из сервиса
        } catch (UsernameNotFoundException ignored) {
            return;
        }

        errors.rejectValue("username", "", "Пользователь уже существует");
    }
}
