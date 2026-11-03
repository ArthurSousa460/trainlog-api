package com.trainlog.repository;

import com.trainlog.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ExerciseRepository extends JpaRepository<Exercise, UUID> {
    boolean existsByNameAndUser_Id(String name, UUID userId);
    boolean existsByNameAndUser_IdAndIdNot(String name, UUID userId, UUID id);
    Optional<Exercise> findByIdAndUser_id(UUID id, UUID userId);
}
