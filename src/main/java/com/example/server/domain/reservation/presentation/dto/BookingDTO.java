package com.example.server.domain.reservation.presentation.dto;

import java.util.List;

public record BookingDTO(
    Long tableId,
    List<String> schoolNumbers,
    String reason,
    List<Integer> periods
){}
