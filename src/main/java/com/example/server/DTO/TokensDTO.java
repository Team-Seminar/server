package com.example.server.DTO;

import lombok.Builder;

@Builder
public record TokensDTO(
        String refreshToken,
        String accessToken
) {
}
