package com.running.tracker.data.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsResponse {

    private int runsCount;
    private int totalDistance;
    private int avgSpeed;
}
