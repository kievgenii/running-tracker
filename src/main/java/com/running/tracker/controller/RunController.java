package com.running.tracker.controller;

import com.running.tracker.data.Run;
import com.running.tracker.data.request.RunFilter;
import com.running.tracker.data.request.RunFinishRequest;
import com.running.tracker.data.request.RunStartRequest;
import com.running.tracker.service.RunService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(RunController.API_PATH)
public class RunController {

    public static final String API_PATH = "/api/v1/run";

    private final RunService runService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Run> getRunsPage(RunFilter filter,
                                 @PageableDefault(sort = "startDateTime", direction = Sort.Direction.DESC) Pageable pageable) {

        return runService.getRunsPage(filter, pageable);
    }

    @GetMapping("{runId}")
    @ResponseStatus(HttpStatus.OK)
    public Run getRun(@PathVariable Long runId) {
        return runService.getRun(runId);
    }

    @PostMapping("/start")
    @ResponseStatus(HttpStatus.CREATED)
    public Run startRun(@RequestBody RunStartRequest startRequest) {
        return runService.startRun(startRequest);
    }

    @PostMapping("{runId}/finish")
    @ResponseStatus(HttpStatus.OK)
    public Run finishRun(@RequestBody RunFinishRequest finishRequest, @PathVariable Long runId) {
        return runService.finishRun(runId, finishRequest);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRun(@PathVariable Long id) {
        runService.deleteRun(id);
    }

}
