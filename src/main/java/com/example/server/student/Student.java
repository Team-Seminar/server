package com.example.server.student;

import com.example.server.user.User;
import com.example.server.user.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

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
