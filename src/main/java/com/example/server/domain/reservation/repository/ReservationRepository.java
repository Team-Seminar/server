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

    List<Reservation> findAllByClassroom(Classroom classroom);

    boolean existsByPassword(String password);

    Optional<Reservation> findByClassroomAndPasswordAndStatus(Classroom classroom, String password, ReservationStatus status);

    long deleteAllByCreateAtBefore(LocalDateTime createAtBefore);
}
