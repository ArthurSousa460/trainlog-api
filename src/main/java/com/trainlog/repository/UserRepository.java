package com.trainlog.repository;

import com.trainlog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByName(String username);

    Optional<User> findByEmail(String email);
}
