package com.trainlog.repository;

import com.trainlog.entity.SerieSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SerieSessionRepository extends JpaRepository<SerieSession, UUID> {
}
