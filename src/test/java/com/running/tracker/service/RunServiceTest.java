package com.running.tracker.service;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import com.running.tracker.data.Run;
import com.running.tracker.data.request.RunFilter;
import com.running.tracker.data.request.RunFinishRequest;
import com.running.tracker.data.request.RunStartRequest;
import com.running.tracker.exception.BadRequestException;
import com.running.tracker.repository.RunRepository;
import com.running.tracker.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class RunServiceTest {

    public static final long USER_ID = -1L;
    @Mock
    private RunRepository runRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Pageable pageable;

    @Mock
    private RunFilter runFilter;

    private RunService runService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        runService = new RunService(runRepository, userRepository);
    }

    @Test
    public void testGetRunsPage() {
        Page<Run> expectedPage = mock(Page.class);
        when(runRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(expectedPage);

        Page<Run> result = runService.getRunsPage(runFilter, pageable);

        verify(runRepository).findAll(any(Specification.class), eq(pageable));
        assertEquals(expectedPage, result);
    }

    @Test
    public void testGetRun() {
        Long runId = 1L;
        Run expectedRun = new Run();
        when(runRepository.getById(runId)).thenReturn(expectedRun);

        Run result = runService.getRun(runId);

        verify(runRepository).getById(runId);
        assertEquals(expectedRun, result);
    }

    @Test(expected = BadRequestException.class)
    public void testStartRunWithUnfinishedRunExists() {
        RunStartRequest startRequest = mock(RunStartRequest.class);
        startRequest.setUserId(USER_ID);
        when(runRepository.existsByUserIdAndFinishDateTimeIsNull(USER_ID)).thenReturn(true);

        runService.startRun(startRequest);
    }

    @Test(expected = BadRequestException.class)
    public void testFinishRunWithInvalidFinishTime() {
        Long runId = 1L;
        RunFinishRequest finishRequest = mock(RunFinishRequest.class);
        Run run = mock(Run.class);
        when(runRepository.getById(runId)).thenReturn(run);
        when(run.getStartDateTime()).thenReturn(LocalDateTime.now());
        when(finishRequest.getFinishDateTime()).thenReturn(LocalDateTime.now().minusDays(1));

        runService.finishRun(runId, finishRequest);
    }

    @Test
    public void testDeleteRun() {
        Long runId = 1L;

        runService.deleteRun(runId);

        verify(runRepository).deleteById(runId);
    }
}
