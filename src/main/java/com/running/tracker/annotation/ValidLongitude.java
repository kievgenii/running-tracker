package com.running.tracker.annotation;

import com.running.tracker.valudator.LongitudeValidator;

import java.lang.annotation.*;
import javax.validation.*;

@Documented
@Constraint(validatedBy = LongitudeValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidLongitude {
    String message() default "Invalid longitude";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

