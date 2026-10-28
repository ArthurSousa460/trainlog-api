package com.trainlog.dto;

import java.time.Instant;

public record ResponseErrorDTO(
        int status,
        String error,
        String message,
        Instant timestamp
) {
}
