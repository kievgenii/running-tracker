package com.running.tracker.service;

import com.running.tracker.data.Run;
import com.running.tracker.data.request.RunFilter;
import com.running.tracker.data.response.StatisticsResponse;
import com.running.tracker.repository.RunRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

import static com.running.tracker.service.AvgSpeedCalculator.calculateAvgSpeed;

@Service
@RequiredArgsConstructor
public class UserStatisticsService {

    private final RunRepository runRepository;

    public StatisticsResponse getUserStatistic(Long userId, LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        RunFilter runFilter = RunFilter.builder()
                .userId(userId)
                .fromDateTime(fromDateTime)
                .toDateTime(toDateTime)
                .build();

        Specification<Run> runSpecification = SpecificationBuilder.getRunSpecification(runFilter);

        List<Run> runs = runRepository.findAll(runSpecification);
        BigDecimal totalDistance = runs.stream()
                .map(Run::getDistance)
                .map(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal totalDuration = runs.stream()
                .map(Run::duration)
                .map(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        double avgSpeed = calculateAvgSpeed(totalDistance.doubleValue(), totalDuration.longValue());

        return StatisticsResponse.builder()
                .runsCount(runs.size())
                .totalDistance(totalDistance.doubleValue())
                .avgSpeed(avgSpeed)
                .build();
    }
}
