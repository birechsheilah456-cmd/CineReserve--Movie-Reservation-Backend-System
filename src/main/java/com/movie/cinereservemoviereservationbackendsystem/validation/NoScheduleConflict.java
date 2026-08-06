package com.movie.cinereservemoviereservationbackendsystem.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NoScheduleConflictValidator.class)
@Target({ElementType.TYPE}) // Applied to DTO / Entity classes to check cross-field parameters
@Retention(RetentionPolicy.RUNTIME)
public @interface NoScheduleConflict {
    String message() default "Schedule conflict detected for this auditorium at the specified time";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}