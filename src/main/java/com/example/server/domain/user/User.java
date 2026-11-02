package com.example.server.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;



@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
public class User {
    //기본키
    @Id
    @Builder.Default
    final String id=UUID.randomUUID().toString();

    //역할 고정
    //선생님 or 학생
    @Column(nullable = false, updatable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    //로그인 아이디
    @Column(unique = true, nullable = false)
    private String loginId;

    //비밀번호
    @Column(nullable = false)
    @JsonIgnore
    private String pw;

}
