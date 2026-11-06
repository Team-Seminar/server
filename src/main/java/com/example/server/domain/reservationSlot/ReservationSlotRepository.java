package com.example.server.domain.reservationSlot;

import com.example.server.domain.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationSlotRepository extends JpaRepository<ReservationSlot, String> {

    @Query("""
        SELECT rp.period
        FROM ReservationSlot rp
        WHERE rp.reservation.id = :reservationId
    """)
    List<Integer> findPeriodsByReservation_Id(Long reservationId);

    //예약에 포함된 학생 반환
    @Query("SELECT DISTINCT r.student FROM ReservationSlot r WHERE r.reservation.id = :reservationId")
    List<Student> findDistinctStudentsByReservationId(@Param("reservationId") String reservationId);
}
