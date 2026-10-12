package com.example.server.reservation.presentation.dto;

import com.example.server.reservation.ReservationStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ReadReservationDTO(
        Long id, //식별
        String reason, //예약 사유
        String className, //교실 이름
        String password, //예약 비밀번호
        ReservationStatus status, // 예약 상태
        LocalDateTime createAt //생성 날짜
) {
}
