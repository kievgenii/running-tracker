package com.running.tracker.service;

import com.running.tracker.data.Run;
import com.running.tracker.data.request.RunFilter;
import com.running.tracker.data.request.RunFinishRequest;
import com.running.tracker.data.request.RunStartRequest;
import com.running.tracker.exception.BadRequestException;
import com.running.tracker.repository.RunRepository;
import com.running.tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import static com.running.tracker.service.AvgSpeedCalculator.THOUSAND;
import static com.running.tracker.service.AvgSpeedCalculator.calculateAvgSpeed;

@Service
@RequiredArgsConstructor
public class RunService {

    private final RunRepository runRepository;
    private final UserRepository userRepository;

    public Page<Run> getRunsPage(RunFilter filter, Pageable pageable) {
        Specification<Run> specification = SpecificationBuilder.getRunSpecification(filter);

        return runRepository.findAll(specification, pageable);
    }

    public Run getRun(Long runId) {
        return runRepository.getById(runId);
    }

    public Run startRun(RunStartRequest startRequest) {
        Long userId = startRequest.getUserId();

        validateUser(userId);
        validateUnfinishedRuns(userId);

        Run newRun = new Run();
        newRun.setUserId(startRequest.getUserId());
        newRun.setStartDateTime(startRequest.getStartDateTime());
        newRun.setStartLatitude(startRequest.getStartLatitude());
        newRun.setStartLongitude(startRequest.getStartLongitude());

        return runRepository.save(newRun);
    }

    private void validateUnfinishedRuns(Long userId) {
        boolean unfinishedRunExists = runRepository.existsByUserIdAndFinishDateTimeIsNull(userId);
        if (unfinishedRunExists) {
            throw new BadRequestException("You have already started a run");
        }
    }

    private void validateUser(Long userId) {
        boolean exists = userRepository.existsById(userId);
        if (!exists) {
            throw new BadRequestException("User is not found");
        }
    }

    public Run finishRun(Long runId, RunFinishRequest finishRequest) {
        Run run = runRepository.getById(runId);

        if (run.getDistance() != null) {
            throw new BadRequestException("Run has already finished");
        }

        LocalDateTime startDateTime = run.getStartDateTime();
        LocalDateTime finishDateTime = finishRequest.getFinishDateTime();
        if (finishDateTime.isBefore(startDateTime)) {
            throw new BadRequestException("Finish can't be earlier than start");
        }

        Double distance = getDistance(finishRequest, run);

        run.setDistance(distance);
        run.setFinishDateTime(finishDateTime);
        run.setFinishLatitude(finishRequest.getFinishLatitude());
        run.setFinishLongitude(finishRequest.getFinishLongitude());

        Double avgSpeed = getAvgSpeed(run, distance);
        run.setAvgSpeed(avgSpeed);

        return runRepository.save(run);
    }

    private Double getAvgSpeed(Run run, Double distance) {
        long duration = run.duration();
        return calculateAvgSpeed(distance, duration);
    }

    private Double getDistance(RunFinishRequest finishRequest, Run run) {
        Double finishLatitude = finishRequest.getFinishLatitude();
        Double finishLongitude = finishRequest.getFinishLongitude();
        Double distance = finishRequest.getDistance();
        if (distance == null || distance.compareTo(0d) <= 0) {
            Double startLatitude = run.getStartLatitude();
            Double startLongitude = run.getStartLongitude();

            distance = DistanceCalculator.calculateDistanceByHaversine(startLatitude, startLongitude, finishLatitude, finishLongitude);
        }

        return BigDecimal.valueOf(distance).multiply(THOUSAND).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public void deleteRun(Long runId) {
        runRepository.deleteById(runId);
    }

    public void deleteRunByUserId(Long userId) {
        runRepository.deleteByUserId(userId);
    }
}
