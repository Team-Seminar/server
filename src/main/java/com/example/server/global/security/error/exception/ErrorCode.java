package com.example.server.global.security.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    CLASS_NOT_FOUND("해당 교실 없음", HttpStatus.NOT_FOUND),

    IS_TIME_NOT("예약 가능 시간이 아님", HttpStatus.CONFLICT),
    NOT_ABLE_RESERVATION("예약 불가", HttpStatus.CONFLICT),
    RESERVATION_NOT_FOUND("해당 예약 없음", HttpStatus.NOT_FOUND),

    OVERLAP_JOIN_STUDENT("학생 중복 회원가입 불가", HttpStatus.CONFLICT),
    IS_USE_NAME("이미 사용중인 아이디",HttpStatus.CONFLICT),
    USER_NOT_FOUND("해당 계정 없음", HttpStatus.NOT_FOUND),
    NOT_ALLOW_LOGIN("아이디 또는 비밀번호가 틀림",HttpStatus.UNAUTHORIZED),
    NOT_EQUALS_PASSWORD("비밀번호 불일치",HttpStatus.BAD_REQUEST),

    NOT_ABLE_STUDENT("학생 사용 불가 요청", HttpStatus.FORBIDDEN);

    private final String message;
    private final HttpStatus status;
}
