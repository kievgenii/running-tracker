package com.running.tracker.repository;

import com.running.tracker.data.Run;
import com.running.tracker.exception.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RunRepository extends JpaRepository<Run, Long>, JpaSpecificationExecutor<Run> {

    boolean existsByUserIdAndFinishDateTimeIsNull(Long userId);

    void deleteByUserId(Long userId);

    default Run getById(Long runId) {
        return this.findById(runId)
                .orElseThrow(() -> new EntityNotFoundException("Run with id: " + runId + " is not found"));
    }
}
