package com.example.server.domain.student.presentation.dto;

public record StudentJoinDTO (
        String schoolNumber,
        String loginId,
        String pw,
        String checkPw
){
}
