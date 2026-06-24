package com.example.server.teacher;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TeacherService {

    private TeacherRepository teacherRepository;

    public UUID login(String name, String pw) { //틀렸다면 위에서 에러를 던져 반환값까지 못가게 하는 방식
        Teacher teacher = teacherRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다."));

        if (!teacher.getPw().equals(pw)) { //pw가 틀렸다면
            throw new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        // 성공 시에 ID 반환
        return teacher.getId();
    }

    public String Join(String name, String pw){
        Teacher teacher=new Teacher(name, pw);
        teacherRepository.save(teacher);
        return "회원가입 성공";
    }
}
