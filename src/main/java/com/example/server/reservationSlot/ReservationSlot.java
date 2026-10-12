package com.example.server.reservationSlot;

import com.example.server.reservation.Reservation;
import com.example.server.student.Student;
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

    @Min(value = 1, message = "교시는 최소 1이상이어야 합니다")
    @Max(value = 10, message = "교시는 최대 10이하여야 합니다")
    int period;
}
