package com.example.server.domain.classroom;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.Check;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        check = @CheckConstraint(name = "check_floor", constraint = "floor >= 1 and floor <= 5")
)
public class Classroom {

    //기본키
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //교실 이름
    @Column(nullable = false, length = 20)
    private String name;

    //교실이 존재하는 층
    @Column(nullable = false)
    private int floor;
}
