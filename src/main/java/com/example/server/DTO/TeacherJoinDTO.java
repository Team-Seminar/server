package com.example.server.DTO;

public record TeacherJoinDTO(
        String name,
        String pw,
        String checkPw,
        String teacherPw
) {
}
