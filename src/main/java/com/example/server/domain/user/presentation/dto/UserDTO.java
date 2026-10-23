package com.example.server.domain.user.presentation.dto;

import com.example.server.domain.user.UserRole;

//유저 조회 dto
public record UserDTO(
        String loginId,
        UserRole role
) {
}
