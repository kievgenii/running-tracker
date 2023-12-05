package com.running.tracker.repository;

import com.running.tracker.data.User;
import com.running.tracker.exception.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    default User getById(Long userId) {
        return this.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id: " + userId + " is not found"));
    }
}
