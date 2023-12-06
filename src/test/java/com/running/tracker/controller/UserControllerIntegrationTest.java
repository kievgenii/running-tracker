package com.running.tracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.running.tracker.data.Gender;
import com.running.tracker.data.User;
import com.running.tracker.data.request.UserRequest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicReference;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final Long validUserId = -1L;

    private static final UserRequest newUser = UserRequest.builder()
            .firstName("John")
            .lastName("Test Int")
            .sex(Gender.MALE)
            .birthDate(LocalDate.of(1990, 5, 1))
            .build();

    @Test
    public void testGetUsers() throws Exception {
        mockMvc.perform(get(UserController.API_PATH)
                .param("page", "0")
                .param("size", "10")
                .param("sort", "id,desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists());
    }

    @Test
    public void testGetUserById() throws Exception {
        mockMvc.perform(get(UserController.API_PATH + "/" + validUserId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(validUserId));
    }

    @Test
    public void testCreateUser() throws Exception {

        AtomicReference<User> userAtomicReference = new AtomicReference<>(new User());

        mockMvc.perform(post(UserController.API_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
                .andDo(result -> {
                    byte[] contentAsByteArray = result.getResponse().getContentAsByteArray();
                    String s = new String(contentAsByteArray, StandardCharsets.UTF_8);
                    userAtomicReference.set(objectMapper.readValue(s, User.class));
                })
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("John"));

        mockMvc.perform(delete(UserController.API_PATH + "/" + userAtomicReference.get().getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testUpdateUser() throws Exception {
        UserRequest updatedUser = new UserRequest();
        updatedUser.setFirstName("Jane");
        updatedUser.setLastName("Test");
        updatedUser.setBirthDate(newUser.getBirthDate());
        updatedUser.setSex(newUser.getSex());

        mockMvc.perform(put(UserController.API_PATH + "/" + validUserId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Jane"));
    }
}

