package com.example.server.DTO;

import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Builder
public record ResponseDTO(
        boolean success,
        Object data
) {
    public static ResponseDTO success(Object data){
        return ResponseDTO.builder()
                .success(true)
                .data(data)
                .build();
    }
    public static ResponseEntity<ResponseDTO> success(Object data, HttpStatus status){
        ResponseDTO resBody= success(data);
        return ResponseEntity.status(status).body(resBody);
    }

    public static ResponseEntity<ResponseDTO> error(String message, HttpStatus status){
        Map<String, ?> data=Map.of("message", message, "status", status);
        ResponseDTO resBody= ResponseDTO.builder()
                .success(false)
                .data(data)
                .build();
        return ResponseEntity.status(status).body(resBody);
    }


}
