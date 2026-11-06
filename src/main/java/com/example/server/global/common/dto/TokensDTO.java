package com.example.server.global.common.dto;

import lombok.Builder;

//토큰 dto
@Builder
public record TokensDTO(
        String refreshToken,
        String accessToken
) {
}
