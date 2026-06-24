package com.example.server.request;

import com.example.server.classroom.Classroom;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "Request")
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotNull
    private int time;

    @NonNull
    @NotBlank
    private String reason;
    private requestStatus status=requestStatus.READY;

    @NonNull
    @NotNull
    @ManyToOne
    private Classroom classroom;

    public void updateReason(String reason){
        this.reason=reason;
    }
    public void updateStatus(requestStatus status){
        this.status=status;
    }
}
