package com.example.server.user;

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
    @Id
    @Builder.Default
    final UUID id=UUID.randomUUID();

    //역할 고정
    @Column(nullable = false, updatable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    @Column(unique = true, nullable = false)
    private String loginId;

    @Column(nullable = false)
    @JsonIgnore
    private String pw;

}
