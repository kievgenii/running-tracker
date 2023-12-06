package com.running.tracker.data.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RunFilter {

    private Long userId;
    private LocalDateTime fromDateTime;
    private LocalDateTime toDateTime;
}
