package com.example.server.domain.reservation;

import com.example.server.domain.classroom.Classroom;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {
    //기본키
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //이유
    @Column(nullable = false)
    private String reason;

    //예약 상태
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Setter
    private ReservationStatus status= ReservationStatus.READY;

    //교실-외래키
    @ManyToOne
    private Classroom classroom;

    //예약 비밀번호
    @Column(nullable = false, unique = true)
    @JsonIgnore
    private String password;

    //생성날자
    @Column(nullable = false)
    @Builder.Default
    private LocalDateTime createAt=LocalDateTime.now();
}
