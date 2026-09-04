package com.example.server.reservation;

import com.example.server.classroom.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByClassroom(Classroom classroom);

    @Query(nativeQuery = true, value = "update User u set u.status='REFUSE' where u.startAt between :start and :end")
    @Modifying
    void updateRefuse(@Param("start") int start, @Param("end") int end);
}
