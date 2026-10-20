package com.example.server.global.security.error;

import com.example.server.global.common.dto.ResponseDTO;
import com.example.server.global.security.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResponseDTO<Map<String, ?>>> handleLoginFalse(CustomException e){
        return ResponseDTO.error(e.getCode().getMessage(), e.getCode().getStatus());
    }
}