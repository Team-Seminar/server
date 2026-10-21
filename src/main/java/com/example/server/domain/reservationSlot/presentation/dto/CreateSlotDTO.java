package com.example.server.domain.reservationSlot.presentation.dto;

import com.example.server.domain.reservation.Reservation;
import lombok.Builder;

import java.util.UUID;

@Builder
public record CreateSlotDTO(
        String schoolNumber,
        long reservationId,
        int period
) {
}
