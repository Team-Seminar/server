package com.example.server.domain.student.presentation;

public record StudentJoinDTO (
        String schoolNumber,
        String loginId,
        String pw,
        String checkPw
){
}
