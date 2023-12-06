package com.running.tracker.valudator;

import com.running.tracker.annotation.ValidLongitude;

import javax.validation.*;

public class LongitudeValidator implements ConstraintValidator<ValidLongitude, Double> {

    @Override
    public void initialize(ValidLongitude constraintAnnotation) {
    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        return value != null && value >= -180 && value <= 180;
    }
}

