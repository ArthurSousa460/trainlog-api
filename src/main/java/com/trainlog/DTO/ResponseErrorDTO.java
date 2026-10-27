package com.trainlog.DTO;

import java.time.Instant;

public record ResponseErrorDTO(
        int status,
        String error,
        String message,
        Instant timestamp
) {
}
