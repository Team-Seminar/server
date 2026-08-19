package com.example.server.reservation;

import com.example.server.classroom.Classroom;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int time;

    @Column(nullable = false)
    private String reason;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private reservationStatus status=reservationStatus.READY;

    @ManyToOne
    private Classroom classroom;

    @Column(nullable = false)
    private String name; //예약자 명

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    public void updateStatus(reservationStatus status){
        this.status=status;
    }
}
