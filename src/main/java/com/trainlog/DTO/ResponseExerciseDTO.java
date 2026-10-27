package com.trainlog.DTO;

import com.trainlog.entity.Exercise;

import java.util.UUID;

public record ResponseExerciseDTO(
    UUID id,
    String name,
    String media_url,
    String type
) {
    public ResponseExerciseDTO(Exercise exercise) {
        this(
                exercise.getId(),
                exercise.getName(),
                exercise.getMedia_url(),
                exercise.getExerciseType().getName());
    }
}
