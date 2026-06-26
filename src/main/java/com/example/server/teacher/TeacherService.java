package com.example.server.teacher;

import com.example.server.global.security.TeacherToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TeacherService {

    @Autowired
    final private TeacherRepository teacherRepository;

    final private TeacherToken teacherToken;

    public String login(String name, String pw) { //틀렸다면 위에서 에러를 던져 반환값까지 못가게 하는 방식
        Teacher teacher = teacherRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다."));

        if (!teacher.getPw().equals(pw)) { //pw가 틀렸다면
            throw new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        return teacherToken.create(teacher.getId(),"teacher");
    }

    public String Join(String name, String pw){
        Teacher teacher=new Teacher(name, pw);
        teacherRepository.save(teacher);
        return "회원가입 성공";
    }
}
