package com.example.server.DTO;

import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Builder
public record ResponseDTO<T>(
        boolean success,
        T data
) {
    public static<T> ResponseDTO<T> success(T data){
        return ResponseDTO.<T>builder()
                .success(true)
                .data(data)
                .build();
    }

    public static ResponseEntity<ResponseDTO<Map<String, ?>>> error(String message, HttpStatus status){
        Map<String, ?> data=Map.of("message", message, "status", status);
        ResponseDTO<Map<String, ?>> resBody= ResponseDTO.<Map<String, ?>>builder()
                .success(false)
                .data(data)
                .build();
        return ResponseEntity.status(status).body(resBody);
    }


}
