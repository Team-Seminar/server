package com.example.server.student;

import com.example.server.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
//tete
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Student{
    @Id
    @OneToOne
    private User user;

    @Column(length = 4, nullable = false)
    private String schoolNumber; //학번
}
