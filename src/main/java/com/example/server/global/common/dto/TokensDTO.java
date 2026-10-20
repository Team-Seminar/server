package com.example.server.global.common.dto;

import lombok.Builder;

@Builder
public record TokensDTO(
        String refreshToken,
        String accessToken
) {
}
