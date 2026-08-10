package com.example.server.classroom;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "Classroom")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ClassroomStatus status = ClassroomStatus.EMPTY;

    private int floor;
    public void UpdateStatus(ClassroomStatus newStatus){
        this.status=newStatus;
    }
}
