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
public class RunStartRequest {

    @NotNull
    private Long userId;
    @NotNull
    private Double startLatitude;
    @NotNull
    private Double startLongitude;
    private LocalDateTime startDateTime = LocalDateTime.now();
}
