package com.trainlog.dto.responses;

import com.trainlog.entity.User;

public record UserResponseDTO(
        String id,
        String name,
        String email
) {
    public UserResponseDTO(User user){
        this(
                user.getId().toString(),
                user.getName(),
                user.getEmail()

        );
    }
}
