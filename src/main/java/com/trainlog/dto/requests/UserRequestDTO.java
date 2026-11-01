package com.trainlog.dto.requests;

public record UserRequestDTO(
        String name,
        String email,
        String password
) {
}
