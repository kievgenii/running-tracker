package com.running.tracker.service;

import com.running.tracker.data.User;
import com.running.tracker.data.request.UserRequest;
import com.running.tracker.repository.RunRepository;
import com.running.tracker.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RunRepository runRepository;

    @Mock
    private Pageable pageable;

    private UserService userService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, runRepository);
    }

    @Test
    public void testGetUsers() {
        Page<User> expectedPage = mock(Page.class);
        when(userRepository.findAll(pageable)).thenReturn(expectedPage);

        Page<User> result = userService.getUsers(pageable);

        verify(userRepository).findAll(pageable);
        assertEquals(expectedPage, result);
    }

    @Test
    public void testGetUser() {
        Long userId = 1L;
        User expectedUser = new User();
        when(userRepository.getById(userId)).thenReturn(expectedUser);

        User result = userService.getUser(userId);

        verify(userRepository).getById(userId);
        assertEquals(expectedUser, result);
    }

    @Test
    public void testCreateUser() {
        UserRequest userRequest = mock(UserRequest.class);
        User expectedUser = new User();
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);

        User result = userService.createUser(userRequest);

        verify(userRepository).save(any(User.class));
        assertEquals(expectedUser, result);
    }

    @Test
    public void testUpdateUser() {
        Long userId = 1L;
        UserRequest userRequest = mock(UserRequest.class);
        User expectedUser = new User();
        when(userRepository.getById(userId)).thenReturn(expectedUser);
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);

        User result = userService.updateUser(userId, userRequest);

        verify(userRepository).getById(userId);
        verify(userRepository).save(any(User.class));
        assertEquals(expectedUser, result);
    }

    @Test
    public void deleteUser() {
        Long userId = 1L;

        userService.deleteUser(userId);

        verify(runRepository).deleteByUserId(userId);
        verify(userRepository).deleteById(userId);
    }
}
