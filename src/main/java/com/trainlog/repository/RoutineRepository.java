package com.trainlog.repository;

import com.trainlog.entity.Routine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoutineRepository extends JpaRepository<Routine, UUID> {
}
