package com.example.server.reservationSlot;

import com.example.server.reservation.Reservation;
import com.example.server.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationSlotRepository extends JpaRepository<ReservationSlot, Long> {

    //예약에 포함된 학생 반환
    @Query("SELECT DISTINCT r.student FROM ReservationSlot r WHERE r.reservation.id = :reservationId")
    List<Student> findDistinctStudentsByReservationId(@Param("reservation") Long reservationId);
}
