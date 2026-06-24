package com.example.server.teacher;

import com.example.server.DTO.TeacherLoginDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequestMapping("api/v1/teachers")
@RestController
public class TeacherController {
    final private TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @RequestMapping("/login")
    public UUID Login(TeacherLoginDTO teacherLoginDTO){
        return teacherService.login(teacherLoginDTO.getName(),teacherLoginDTO.getPw());
    }
}
