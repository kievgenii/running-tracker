package com.running.tracker.valudator;

import com.running.tracker.annotation.ValidLatitude;

import javax.validation.*;

public class LatitudeValidator implements ConstraintValidator<ValidLatitude, Double> {

    @Override
    public void initialize(ValidLatitude constraintAnnotation) {
    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        return value != null && value >= -90 && value <= 90;
    }
}

