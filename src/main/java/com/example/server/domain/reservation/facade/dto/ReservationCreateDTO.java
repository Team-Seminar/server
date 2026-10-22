package com.example.server.domain.reservation.facade.dto;

import lombok.Builder;

@Builder
public record ReservationCreateDTO(
        Long tableId,
        String reason
) {
}
