package com.running.tracker.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "runs")
public class Run {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Double startLatitude;

    private Double startLongitude;

    private Double finishLatitude;

    private Double finishLongitude;

    private LocalDateTime startDateTime;

    private LocalDateTime finishDateTime;

    private Double distance;

    private Double avgSpeed;

    public long duration() {
        LocalDateTime finish = LocalDateTime.now();
        if (this.finishDateTime != null) {
            finish = this.finishDateTime;
        }

        return Duration.between(this.startDateTime, finish).toSeconds();
    }
}
