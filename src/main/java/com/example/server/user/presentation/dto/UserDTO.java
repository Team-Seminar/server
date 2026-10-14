package com.example.server.user.presentation.dto;

import com.example.server.user.UserRole;

public record UserDTO(
        String loginId,
        UserRole role
) {
}
