package com.example.server.domain.reservationSlot;

import com.example.server.domain.reservation.Reservation;
import com.example.server.domain.student.Student;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @Id
    Long id;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    Reservation reservation;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    @JoinColumn(name = "student_id")
    Student student;

    int period;
}
