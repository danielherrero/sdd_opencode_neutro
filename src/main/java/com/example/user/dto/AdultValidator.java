package com.example.user.dto;

import java.time.LocalDate;
import java.time.Period;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AdultValidator implements ConstraintValidator<Adult, LocalDate> {

    @Override
    public boolean isValid(LocalDate fechaNacimiento, ConstraintValidatorContext context) {
        return fechaNacimiento == null || Period.between(fechaNacimiento, LocalDate.now()).getYears() >= 18;
    }
}
