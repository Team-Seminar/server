package com.example.server.teacher;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@NoArgsConstructor
public class TeacherService {

    private TeacherRepository teacherRepository;

    public UUID login(String name, String pw) {
        // 1. 이름으로 선생님을 찾고, 없으면 예외 발생
        Teacher teacher = teacherRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다."));

        // 2. 비밀번호 비교 후 다르면 예외 발생
        if (!teacher.getPw().equals(pw)) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        // 3. 성공 시에만 UUID 반환
        return teacher.getId();
    }
}
