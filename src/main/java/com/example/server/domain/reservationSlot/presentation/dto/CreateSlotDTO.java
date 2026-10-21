package com.example.server.domain.reservationSlot.presentation.dto;

import com.example.server.domain.reservation.Reservation;
import com.example.server.domain.student.Student;

public record CreateSlotDTO(
        Student student,
        Reservation reservation,
        int period
) {
}
