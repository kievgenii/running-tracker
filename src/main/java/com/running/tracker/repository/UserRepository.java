package com.running.tracker.repository;

import com.running.tracker.data.User;
import com.running.tracker.exception.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    default User getById(Long id) {
        return this.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id: " + id + " is not found"));
    }

    boolean existsById(Long id);
}
