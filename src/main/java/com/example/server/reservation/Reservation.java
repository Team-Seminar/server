package com.example.server.reservation;

import com.example.server.classroom.Classroom;
import com.example.server.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

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
    int startAt;
    @Column(nullable = false)
    int endAt;

    @Column(nullable = false)
    private String reason;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationStatus status= ReservationStatus.READY;

    @ManyToOne
    private Classroom classroom;

    @Column(nullable = false)
    private List<User> groups;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    public void updateStatus(ReservationStatus status){
        this.status=status;
    }
}
