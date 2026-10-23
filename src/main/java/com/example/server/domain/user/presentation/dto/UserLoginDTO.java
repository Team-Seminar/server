package com.example.server.domain.user.presentation.dto;

//유저 로그인 dto
public record UserLoginDTO (
    String loginId,
    String pw
){}
