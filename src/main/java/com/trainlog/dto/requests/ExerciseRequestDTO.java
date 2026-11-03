package com.trainlog.dto.requests;

import java.util.UUID;

public record ExerciseRequestDTO(
        String name,
        String media_url,
        UUID type

) {
}
