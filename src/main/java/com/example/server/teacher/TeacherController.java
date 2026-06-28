package com.example.server.teacher;

import com.example.server.global.ResponseClass;
import com.example.server.DTO.TeacherJoinDTO;
import com.example.server.DTO.TeacherLoginDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RequestMapping("api/v1/teachers")
@RestController
@RequiredArgsConstructor
public class TeacherController {
    final private TeacherService teacherService;

    @Value("${teacher-key}")
    private String TEACHER_KEY;
    ResponseClass responseClass=new ResponseClass();

    @PostMapping("/login")
    public ResponseEntity<?> Login(@RequestBody TeacherLoginDTO teacherLoginDTO){
        return responseClass.tokenReturn(teacherService.login(teacherLoginDTO.getName(),teacherLoginDTO.getPw()));
    }


    @PostMapping()
    public ResponseEntity<?> Join(@RequestBody TeacherJoinDTO teacherJoinDTO){
        ResponseClass responseClass=new ResponseClass();
        if (!Objects.equals(teacherJoinDTO.getTeacherPw(), TEACHER_KEY)){
            System.out.println("선생님이 아닙니다.");
            return responseClass.massageReturn("선생님이 아닙니다.");
        }
        return responseClass.massageReturn(teacherService.Join(teacherJoinDTO.getName(),teacherJoinDTO.getPw()));
    }
}
