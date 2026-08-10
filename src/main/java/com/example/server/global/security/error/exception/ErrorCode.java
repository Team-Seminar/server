package com.example.server.global.security.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    CLASS_NOT_FOUND("해당 교실 없음", HttpStatus.NOT_FOUND),

    RESERVATION_NOT_FOUND("해당 예약 없음", HttpStatus.NOT_FOUND),

    TEACHER_NOT_FOUND("해당 계정 없음", HttpStatus.NOT_FOUND),
    NOT_ALLOW_LOGIN("아이디 또는 비밀번호가 틀림",HttpStatus.UNAUTHORIZED),

    NOT_ABLE_STUDENT("학생 사용 불가 요청", HttpStatus.FORBIDDEN);

    private final String message;
    private final HttpStatus status;
}
