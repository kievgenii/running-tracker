package com.running.tracker.controller;

import com.running.tracker.data.response.StatisticsResponse;
import com.running.tracker.service.UserStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping(UserStatisticController.API_PATH)
public class UserStatisticController {

    public static final String API_PATH = "/api/v1/user-statistic";

    private final UserStatisticsService userStatisticsService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public StatisticsResponse getUserStatistic(@RequestParam Long userId,
                                               @RequestParam(required = false) LocalDateTime fromDateTime,
                                               @RequestParam(required = false) LocalDateTime toDateTime) {

        return userStatisticsService.getUserStatistic(userId, fromDateTime, toDateTime);
    }
}
