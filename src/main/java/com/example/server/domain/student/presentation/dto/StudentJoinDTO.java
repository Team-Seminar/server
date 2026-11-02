package com.example.server.domain.student.presentation.dto;

//학생 회원가입 dto
public record StudentJoinDTO (
        String schoolNumber,
        String loginId,
        String pw,
        String checkPw
){
}
