package com.example.server.reservation;

import com.example.server.classroom.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findById(Long id);

    List<Reservation> findAllByClassroom(Classroom classroom);
}
