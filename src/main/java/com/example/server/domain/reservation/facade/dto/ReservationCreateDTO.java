package com.example.server.domain.reservation.facade.dto;

import lombok.Builder;

//예약 객체 생성 api (예약 dto와 예약 생성 dto는 별개임)
@Builder
public record ReservationCreateDTO(
        Long classroomId,
        String reason
) {
}
