package com.example.server.teacher;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class TeacherService {

    private TeacherRepository teacherRepository;

    public String Login(String name, String pw){
        Teacher teacher=teacherRepository.findByName(name).orElseThrow();
        if(teacher.getPw().equals(pw)){
            return "성공";
        }
        return "실패";
    }
}
