package com.running.tracker.data.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RunFinishRequest {

    @NotNull
    private Double finishLatitude;
    @NotNull
    private Double finishLongitude;
    private Double distance;
    private LocalDateTime finishDateTime = LocalDateTime.now();
}
