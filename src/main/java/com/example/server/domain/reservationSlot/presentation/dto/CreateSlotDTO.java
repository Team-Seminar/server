package com.example.server.domain.reservationSlot.presentation.dto;

import lombok.Builder;

//교시 엔티티 생성을 위한 dto
@Builder
public record CreateSlotDTO(
        String schoolNumber,
        long reservationId,
        int period
) {
}
