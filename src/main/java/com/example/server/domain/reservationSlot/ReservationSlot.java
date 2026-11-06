package com.example.server.domain.reservationSlot;

import com.example.server.domain.reservation.Reservation;
import com.example.server.domain.student.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        check = @CheckConstraint(name = "check_period", constraint = "period>=1 and period<=10"),
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_reservation_slot_student_period",
                        columnNames = {"student_id", "period"}
                )
        }
)
public class ReservationSlot {
    @Builder.Default
    @Id
    String id = UUID.randomUUID().toString();

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    Reservation reservation;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    @JoinColumn(name = "student_id")
    Student student;

    int period;
}
