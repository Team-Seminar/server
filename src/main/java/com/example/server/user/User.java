package com.example.server.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "member")
public class User {
    @Id
    @Builder.Default
    final private UUID id=UUID.randomUUID();

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    @JsonIgnore
    private String pw;

    @Column(nullable = false)
    private UserRole role;
}
