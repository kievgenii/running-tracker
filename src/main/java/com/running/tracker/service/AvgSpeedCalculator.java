package com.running.tracker.service;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.RoundingMode;

@UtilityClass
public class AvgSpeedCalculator {

    public static final BigDecimal THOUSAND = BigDecimal.valueOf(100);

    public static double calculateAvgSpeed(Double distance, long duration) {
        BigDecimal hours = BigDecimal.valueOf(duration).divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP).divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);
        return BigDecimal.valueOf(distance).divide(THOUSAND, 2, RoundingMode.HALF_UP).divide(hours, 2, RoundingMode.HALF_UP).doubleValue();
    }
}
