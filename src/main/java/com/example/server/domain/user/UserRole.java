package com.example.server.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserRole {
    STUDENT("ROLE_STUDENT"),
    TEACHER("ROLE_TEACHER");

    private final String role;

    //enum에서 바로 토큰에서 role에 쓰기 위한 메서드
    public String toStr(){
        return this.role;
    }
}
