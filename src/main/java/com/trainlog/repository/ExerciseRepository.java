package com.trainlog.repository;

import com.trainlog.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExerciseRepository extends JpaRepository<Exercise, UUID> {
    public boolean existsByName(String name);
}
