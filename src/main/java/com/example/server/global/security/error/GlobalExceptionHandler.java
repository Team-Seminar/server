package com.example.server.global.security.error;

import com.example.server.global.ResponseClass;
import com.example.server.global.security.error.exception.LoginException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    final private ResponseClass responseClass;
    @ExceptionHandler(LoginException.class)
    public ResponseEntity<?> handleLoginFalse(LoginException e){
        return responseClass.massageReturn("로그인 실패");
    }
}