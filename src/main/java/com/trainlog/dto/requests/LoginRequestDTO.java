package com.trainlog.dto.requests;

public record LoginRequestDTO(
        String email,
        String password
) {
}
