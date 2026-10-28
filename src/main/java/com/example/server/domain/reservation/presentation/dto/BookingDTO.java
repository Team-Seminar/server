package com.example.server.domain.reservation.presentation.dto;

import java.util.List;

//request body 를 위한 dto
public record BookingDTO(
    Long classroomId,
    List<String> schoolNumbers,
    String reason,
    List<Integer> periods
){}
