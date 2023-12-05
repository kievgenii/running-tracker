package com.running.tracker.controller;

import com.running.tracker.data.User;
import com.running.tracker.data.request.UserRequest;
import com.running.tracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping(UserController.API_PATH)
public class UserController {

    public static final String API_PATH = "/api/v1/user";

    private final UserService userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<User> getUsers(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return userService.getUsers(pageable);
    }

    @GetMapping("{userId}")
    @ResponseStatus(HttpStatus.OK)
    public User getUserById(@PathVariable Long userId) {
        Objects.requireNonNull(userId, "UserId shouldn't be not null");
        return userService.getUser(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @PutMapping("{userId}")
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@PathVariable Long userId, @RequestBody UserRequest userRequest) {
        return userService.updateUser(userId, userRequest);
    }

    @DeleteMapping("{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}
