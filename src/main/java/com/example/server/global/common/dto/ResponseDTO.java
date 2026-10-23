package com.example.server.global.common.dto;

import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

//공통 반환 dto
@Builder
public record ResponseDTO<T>(
        boolean success,
        T data
) {
    //성공시 반환을 위한 메서드
    public static<T> ResponseDTO<T> success(T data){
        return ResponseDTO.<T>builder()
                .success(true)
                .data(data)
                .build();
    }

    //에러 발생시 반환을 위한 메서드
    public static ResponseEntity<ResponseDTO<Map<String, ?>>> error(String message, HttpStatus status){
        Map<String, ?> data=Map.of("message", message, "status", status);
        ResponseDTO<Map<String, ?>> resBody= ResponseDTO.<Map<String, ?>>builder()
                .success(false)
                .data(data)
                .build();
        return ResponseEntity.status(status).body(resBody);
    }


}
