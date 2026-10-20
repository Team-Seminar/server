package com.example.server.domain.user.presentation.dto;

public record TeacherJoinDTO(
        String name,
        String pw,
        String checkPw,
        String teacherPw
) {
}
