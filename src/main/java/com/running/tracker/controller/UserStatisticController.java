package com.running.tracker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(UserStatisticController.API_PATH)
public class UserStatisticController {

    public static final String API_PATH = "/api/v1/user-statistic";
}
