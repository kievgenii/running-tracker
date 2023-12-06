package com.running.tracker.service;

import com.running.tracker.data.User;
import com.running.tracker.data.request.UserRequest;
import com.running.tracker.exception.EntityNotFoundException;
import com.running.tracker.repository.RunRepository;
import com.running.tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RunRepository runRepository;

    public Page<User> getUsers(Pageable pageable) {
        Specification<User> spec = Specification.not((root, query, criteriaBuilder) -> criteriaBuilder.isTrue(root.get("test")));
        return userRepository.findAll(spec, pageable);
    }

    public User getUser(Long userId) {
        return userRepository.getById(userId);
    }

    public User createUser(UserRequest userRequest) {
        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setBirthDate(userRequest.getBirthDate());
        user.setSex(userRequest.getSex());

        return userRepository.save(user);
    }

    public User updateUser(Long userId, UserRequest userRequest) {
        User user = userRepository.getById(userId);

        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setBirthDate(userRequest.getBirthDate());
        user.setSex(userRequest.getSex());

        return userRepository.save(user);
    }

    public void deleteUser(Long userId) {

        runRepository.deleteByUserId(userId);
        userRepository.deleteById(userId);
    }
}
