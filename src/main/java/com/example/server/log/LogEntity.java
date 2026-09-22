package com.example.server.log;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogEntity{
    @Builder.Default
    LocalDateTime CreateAt=LocalDateTime.now();

    @Builder.Default
    @Id
    UUID id=UUID.randomUUID();

    @Column(nullable = false)
    String content;

    @Enumerated(value = EnumType.STRING)
    LogStatus logStatus;
}
