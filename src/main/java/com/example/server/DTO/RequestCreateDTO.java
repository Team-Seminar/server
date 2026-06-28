package com.example.server.DTO;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestCreateDTO {
    Long tableId;
    String name;
    String reason;
    int time;
}
