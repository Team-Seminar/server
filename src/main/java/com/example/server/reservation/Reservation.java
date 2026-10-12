package com.example.server.reservation;

import com.example.server.classroom.Classroom;
import com.example.server.student.Student;
import com.example.server.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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
    private String reason;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Setter
    private ReservationStatus status= ReservationStatus.READY;

    @ManyToOne
    private Classroom classroom;

    @Column(nullable = false, unique = true)
    @JsonIgnore
    private String password;

    @Column(nullable = false)
    @Builder.Default
    private LocalDateTime createAt=LocalDateTime.now();
}
