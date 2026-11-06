package com.example.server.domain.reservation.presentation.dto;

//비밀번호 보안을 위해서 dto 작성
public record ReservationUseDTO(
        String reservationPW
) {
}
