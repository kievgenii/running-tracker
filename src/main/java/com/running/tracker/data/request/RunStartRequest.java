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
public class RunStartRequest {

    @NotNull
    private Long userId;
    @NotNull
    @ValidLatitude
    private Double startLatitude;
    @NotNull
    @ValidLongitude
    private Double startLongitude;
    private LocalDateTime startDateTime = LocalDateTime.now();
}
