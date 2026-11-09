package com.example.server.domain.student;

import com.example.server.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Student{
    @Id
    private String id;
    //외래키.
    @MapsId
    @OneToOne
    @JoinColumn(name = "user_id") //에러뜨지만 잘 돌아감
    private User user;

    @Column(length = 4, nullable = false, unique = true)
    private String schoolNumber; //학번
}
