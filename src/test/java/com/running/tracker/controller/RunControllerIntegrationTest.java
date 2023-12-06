package com.running.tracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.running.tracker.data.Run;
import com.running.tracker.repository.RunRepository;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RunControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final Long validUserId = -1L;

    private final Long invalidUserId = 999L;

    private Long validRunId;

    @Test
    public void testStartRunWithValidUser() throws Exception {
        String startRunJson = "{\"userId\":" + validUserId + ",\"startDateTime\":\"2023-01-01T00:00:00\",\"startLatitude\":10.0,\"startLongitude\":20.0}";

        AtomicReference<Run> run = new AtomicReference<>(new Run());

        mockMvc.perform(post(RunController.API_PATH + "/start")
                .contentType(MediaType.APPLICATION_JSON)
                .content(startRunJson))
                .andDo(result -> {
                    byte[] contentAsByteArray = result.getResponse().getContentAsByteArray();
                    String s = new String(contentAsByteArray, StandardCharsets.UTF_8);
                    run.set(objectMapper.readValue(s, Run.class));
                })
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(validUserId));

        validRunId = run.get().getId();
    }

    @Test
    public void testStartRunWithInvalidUser() throws Exception {
        String startRunJson = "{\"userId\":" + invalidUserId + ",\"startDateTime\":\"2023-01-01T00:00:00\",\"startLatitude\":10.0,\"startLongitude\":20.0}";

        mockMvc.perform(post(RunController.API_PATH + "/start")
                .contentType(MediaType.APPLICATION_JSON)
                .content(startRunJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testFinishRunWithValidUser() throws Exception {

        if (validRunId != null) {
            String finishRunJson = "{\"finishDateTime\":\"2023-01-01T01:00:00\",\"finishLatitude\":15.0,\"finishLongitude\":25.0,\"distance\":5.0}";

            mockMvc.perform(post(RunController.API_PATH + "/" + validRunId + "/finish")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(finishRunJson))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.distance").value(500.0));
        }
    }

}
