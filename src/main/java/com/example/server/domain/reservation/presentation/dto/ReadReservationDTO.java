package com.example.server.domain.reservation.presentation.dto;

import com.example.server.domain.reservation.Reservation;
import com.example.server.domain.reservation.ReservationStatus;
import lombok.Builder;

import java.time.LocalDateTime;

//조회를 위한 dto. 필요값만 반환한다
@Builder
public record ReadReservationDTO(
        Long id, //식별
        String reason, //예약 사유
        String classroomName, //교실 이름
        ReservationStatus status, // 예약 상태
        LocalDateTime createAt //생성 날짜
) {
    //엔티티에서 dto로 변환하는 과정이 길므로 메서드로 작성
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
