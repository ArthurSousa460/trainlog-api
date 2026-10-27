package com.trainlog.repository;

import com.trainlog.entity.ExerciseType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExerciseTypeRepository extends JpaRepository<ExerciseType, UUID> {
}
