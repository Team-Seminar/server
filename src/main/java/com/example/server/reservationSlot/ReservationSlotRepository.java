package com.example.server.reservationSlot;

import com.example.server.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationSlotRepository extends JpaRepository<ReservationSlot, Long> {
    long countDistinctStudentByReservation(Reservation reservation);
    long countDistinctStudentByReservation_Id(Long reservationId);
}
