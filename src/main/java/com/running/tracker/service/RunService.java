package com.running.tracker.service;

import com.running.tracker.data.Run;
import com.running.tracker.data.request.RunFilter;
import com.running.tracker.data.request.RunFinishRequest;
import com.running.tracker.data.request.RunStartRequest;
import com.running.tracker.exception.BadRequestException;
import com.running.tracker.exception.EntityNotFoundException;
import com.running.tracker.repository.RunRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RunService {

    private static final BigDecimal THOUSAND = BigDecimal.valueOf(100);

    private final RunRepository runRepository;

    public Page<Run> getRunsPage(RunFilter filter, Pageable pageable) {
        Specification<Run> specification = Specification.where(null);

        Long userId = filter.getUserId();
        if (userId != null) {
            specification = specification.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("userId"), userId));
        }

        LocalDateTime fromDateTime = filter.getFromDateTime();
        if (fromDateTime != null) {
            specification = specification.and(Specification.where((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("startDateTime"), fromDateTime)));
        }

        LocalDateTime toDateTime = filter.getToDateTime();
        if (toDateTime != null) {
            specification = specification.and(Specification.where((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("startDateTime"), toDateTime)));
        }

        return runRepository.findAll(specification, pageable);
    }

    public Run getRun(Long runId) {
        return runRepository.getById(runId);
    }

    public Run startRun(RunStartRequest startRequest) {
        boolean unfinishedRunExists = runRepository.existsByFinishDateTimeIsNull();
        if (unfinishedRunExists) {
            throw new BadRequestException("You have already started a run");
        }

        Run newRun = new Run();
        newRun.setUserId(startRequest.getUserId());
        newRun.setStartDateTime(startRequest.getStartDateTime());
        newRun.setStartLatitude(startRequest.getStartLatitude());
        newRun.setStartLongitude(startRequest.getStartLongitude());

        return runRepository.save(newRun);
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
        BigDecimal hours = BigDecimal.valueOf(duration).divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP).divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);
        return BigDecimal.valueOf(distance).divide(THOUSAND, 2, RoundingMode.HALF_UP).divide(hours, 2, RoundingMode.HALF_UP).doubleValue();
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
}
