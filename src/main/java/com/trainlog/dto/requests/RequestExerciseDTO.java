package com.trainlog.dto.requests;

import java.util.UUID;

public record RequestExerciseDTO(
        UUID id,
        String name,
        String media_url,
        UUID type

) {
}
