package com.trainlog.dto.responses;

public record LoginResponseDTO(
        String type,
        String accessToken
) {
}
