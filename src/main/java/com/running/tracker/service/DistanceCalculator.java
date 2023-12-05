package com.running.tracker.service;

import lombok.experimental.UtilityClass;

public class DistanceCalculator {

    private static final long EARTH_RADIUS = 6371L;

    public static double calculateDistanceByHaversine(double startLat,
                                                      double startLong,
                                                      double endLat,
                                                      double endLong) {

        double dLat = Math.toRadians((endLat - startLat));
        double dLong = Math.toRadians((endLong - startLong));

        startLat = Math.toRadians(startLat);
        endLat = Math.toRadians(endLat);

        double a = haversine(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversine(dLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

    private static double haversine(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }
}
