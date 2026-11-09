package com.example.server.domain.reservationSlot.presentation.dto;

import lombok.Builder;

import java.util.List;

//교시 엔티티 생성을 위한 dto
@Builder
public record CreateSlotDTO(
        String schoolNumber,
        long reservationId,
        List<Integer> period
) {
}
