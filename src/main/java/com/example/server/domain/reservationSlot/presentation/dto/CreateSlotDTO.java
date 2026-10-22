package com.example.server.domain.reservationSlot.presentation.dto;

import lombok.Builder;


@Builder
public record CreateSlotDTO(
        String schoolNumber,
        long reservationId,
        int period
) {
}
