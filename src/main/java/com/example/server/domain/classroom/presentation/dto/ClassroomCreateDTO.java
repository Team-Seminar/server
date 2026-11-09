package com.example.server.domain.classroom.presentation.dto;

//교실 생성 dto
public record ClassroomCreateDTO (
    String name,
    int floor
){}
