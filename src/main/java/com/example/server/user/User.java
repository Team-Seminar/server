package com.example.server.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@MappedSuperclass
@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @Builder.Default
    final UUID id=UUID.randomUUID();

    @Column(unique = true, nullable = false)
    String loginId;

    @Column(nullable = false)
    @JsonIgnore
    String pw;

}
