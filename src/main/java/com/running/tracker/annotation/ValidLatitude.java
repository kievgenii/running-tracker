package com.running.tracker.annotation;

import com.running.tracker.valudator.LatitudeValidator;

import java.lang.annotation.*;
import javax.validation.*;

@Documented
@Constraint(validatedBy = LatitudeValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidLatitude {
    String message() default "Invalid latitude";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

