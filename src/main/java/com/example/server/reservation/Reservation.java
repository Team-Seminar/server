package com.example.server.reservation;

import com.example.server.classroom.Classroom;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotNull
    private int time;

    @NonNull
    @NotBlank
    private String reason;
    private reservationStatus status=reservationStatus.READY;

    @NonNull
    @NotNull
    @ManyToOne
    private Classroom classroom;

    @NotBlank
    @NonNull
    private String name; //예약자 명

    public void updateStatus(reservationStatus status){
        this.status=status;
    }
}
