package com.example.server.domain.reservation.presentation.dto;

import java.util.List;

public record ReservationCreateDTO (
    Long tableId,
    List<String> name,
    String reason,
    int startAt,
    int endAt
){}
