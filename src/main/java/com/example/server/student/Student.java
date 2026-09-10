package com.example.server.student;

import com.example.server.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Student{
    @Id
    private UUID id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "user_id") //에러뜨지만 잘 돌아감
    private User user;

    @Column(length = 4, nullable = false)
    private String schoolNumber; //학번
}
