package com.example.server.teacher;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class Teacher {
    @Id
    private UUID id=UUID.randomUUID();

    @NotBlank
    @NonNull
    private String name;
    @NotBlank
    @NonNull
    private String pw;
}
