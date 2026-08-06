package com.movie.cinereservemoviereservationbackendsystem.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FutureDateValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface FutureDate {
    String message() default "The date must be in the future";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}