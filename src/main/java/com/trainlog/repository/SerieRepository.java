package com.trainlog.repository;

import com.trainlog.entity.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SerieRepository extends JpaRepository<Serie, UUID> {
}
