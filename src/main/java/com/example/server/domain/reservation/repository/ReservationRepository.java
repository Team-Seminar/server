package com.example.server.domain.reservation.repository;

import com.example.server.domain.classroom.Classroom;
import com.example.server.domain.reservation.Reservation;
import com.example.server.domain.reservation.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    //교실 기반 예약 조회
    List<Reservation> findAllByClassroom(Classroom classroom);

    //비밀번호 중복 여부
    boolean existsByPassword(String password);

    //교실, 비밀번호, 상태를 기반으로 조회
    //임베디드 교실 사용으로 상태 변경을 위해 사용
    Optional<Reservation> findByClassroomAndPasswordAndStatus(Classroom classroom, String password, ReservationStatus status);

    //생성일자 이전 전체 삭제 및 개수 반환
    long deleteAllByCreateAtBefore(LocalDateTime createAtBefore);
}
