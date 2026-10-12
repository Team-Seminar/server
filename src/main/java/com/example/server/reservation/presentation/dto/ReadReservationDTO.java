package com.example.server.reservation.presentation.dto;

import com.example.server.reservation.Reservation;
import com.example.server.reservation.ReservationStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ReadReservationDTO(
        Long id, //식별
        String reason, //예약 사유
        String classroomName, //교실 이름
        ReservationStatus status, // 예약 상태
        LocalDateTime createAt //생성 날짜
) {
    public static ReadReservationDTO EntityToDTO(Reservation reservation){
        return ReadReservationDTO.builder()
                .id(reservation.getId())
                .reason(reservation.getReason())
                .classroomName(reservation.getClassroom().getName())
                .status(reservation.getStatus())
                .createAt(reservation.getCreateAt())
                .build();
    }
}
