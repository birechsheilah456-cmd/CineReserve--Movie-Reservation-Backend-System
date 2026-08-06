package com.movie.cinereservemoviereservationbackendsystem.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class FutureDateValidator implements ConstraintValidator<FutureDate, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Let @NotNull handle null checks if needed
        }
        if (value instanceof LocalDate localDate) {
            return localDate.isAfter(LocalDate.now());
        }
        if (value instanceof LocalDateTime localDateTime) {
            return localDateTime.isAfter(LocalDateTime.now());
        }
        return false;
    }
}