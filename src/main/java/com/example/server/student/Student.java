package com.example.server.student;

import com.example.server.user.User;
import com.example.server.user.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class Student extends User {
    //역할 고정
    @Column(nullable = false, updatable = false)
    @Enumerated(value = EnumType.STRING)
    @Builder.Default
    private UserRole role=UserRole.STUDENT;

    @Column(length = 4, nullable = false)
    private String schoolNumber; //학번
}
