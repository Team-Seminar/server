package com.example.server.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum  ErrorCode {
    //토큰 관련 에러
    TOKEN_FAIL("토큰 인증 실패", HttpStatus.UNAUTHORIZED),
    TOKEN_BAD_REQUEST("지원되지 않는 토큰 형식", HttpStatus.UNAUTHORIZED),
    TOKEN_DAMAGED("손상된 토큰", HttpStatus.UNAUTHORIZED),
    TOKEN_INVALID("유효하지 않은 JWT 서명", HttpStatus.UNAUTHORIZED),
    TOKEN_EXPIRATION("만료된 토큰",HttpStatus.UNAUTHORIZED),
    TOKEN_NOT_FOUND("토큰이 존재하지 않음", HttpStatus.UNAUTHORIZED),
    TOKEN_FORGERY("토큰 위조", HttpStatus.UNAUTHORIZED),

    //기타
    NOT_FOUND_WAY("해당 방법을 찾을 수 없음",HttpStatus.INTERNAL_SERVER_ERROR),

    //교실 관련 에러
    CLASS_NOT_FOUND("해당 교실 없음", HttpStatus.NOT_FOUND),

    //예약 관련 에러
    IS_NOT_ALLOW("허용된 예약이 아님", HttpStatus.CONFLICT),
    IS_TIME_NOT("예약 가능 시간이 아님", HttpStatus.CONFLICT),
    NOT_ABLE_RESERVATION("예약 불가", HttpStatus.CONFLICT),
    RESERVATION_NOT_FOUND("해당 예약 없음", HttpStatus.NOT_FOUND),

    //auth 관련 에러
    OVERLAP_JOIN_STUDENT("학생 중복 회원가입 불가", HttpStatus.CONFLICT),
    IS_USE_NAME("이미 사용중인 아이디",HttpStatus.CONFLICT),
    USER_NOT_FOUND("해당 계정 없음", HttpStatus.NOT_FOUND),
    NOT_ALLOW_LOGIN("아이디 또는 비밀번호가 틀림",HttpStatus.UNAUTHORIZED),
    NOT_EQUALS_PASSWORD("비밀번호 불일치",HttpStatus.CONFLICT),

    //학생 관련 에러
    NOT_ABLE_STUDENT("학생 사용 불가 요청", HttpStatus.FORBIDDEN);

    private final String message;
    private final HttpStatus status;
}
