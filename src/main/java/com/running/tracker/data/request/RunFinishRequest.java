package com.running.tracker.data.request;

import com.running.tracker.annotation.ValidLatitude;
import com.running.tracker.annotation.ValidLongitude;
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
    @ValidLatitude
    private Double finishLatitude;
    @NotNull
    @ValidLongitude
    private Double finishLongitude;
    private Double distance;
    private LocalDateTime finishDateTime = LocalDateTime.now();
}
