package com.example.server.teacher;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Teacher {
    @Id
    @Builder.Default
    final private UUID id=UUID.randomUUID();

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    @JsonIgnore
    private String pw;
}
