package com.example.server.domain.reservation.presentation.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReservationCreateDTO {
    Long tableId;
    List<String> name;
    String reason;
    int startAt;
    int endAt;
}
