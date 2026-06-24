package com.example.server.teacher;

import com.example.server.DTO.TeacherJoinDTO;
import com.example.server.DTO.TeacherLoginDTO;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("api/v1/teachers")
@RestController
public class TeacherController {
    final private TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/login")
    public UUID Login(@RequestBody TeacherLoginDTO teacherLoginDTO){
        return teacherService.login(teacherLoginDTO.getName(),teacherLoginDTO.getPw());
    }

    @PostMapping()
    public String Join(@RequestBody TeacherJoinDTO teacherJoinDTO){
        if (!teacherJoinDTO.getTeacherPw().equals("iamteacher")){
            return "선생님이 아닙니다.";
        }
        return teacherService.Join(teacherJoinDTO.getName(),teacherJoinDTO.getPw());
    }
}
