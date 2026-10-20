package com.example.server.domain.user.presentation.dto;

import com.example.server.domain.user.UserRole;

public record UserDTO(
        String loginId,
        UserRole role
) {
}
