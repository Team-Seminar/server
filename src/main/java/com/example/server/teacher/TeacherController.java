package com.example.server.teacher;

import com.example.server.DTO.ResponseDTO;
import com.example.server.DTO.TokensDTO;
import com.example.server.global.ResponseClass;
import com.example.server.DTO.TeacherJoinDTO;
import com.example.server.DTO.TeacherLoginDTO;
import com.example.server.global.security.error.exception.LoginException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RequestMapping("api/v1/teachers")
@RestController
@RequiredArgsConstructor
public class TeacherController {
    final private TeacherService teacherService;

    @Value("${teacher-key}")
    private String TEACHER_KEY;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO Login(@RequestBody TeacherLoginDTO teacherLoginDTO){
        return ResponseDTO.success(teacherService.login(teacherLoginDTO.getName(),teacherLoginDTO.getPw()));
    }


    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO Join(@RequestBody TeacherJoinDTO teacherJoinDTO){
        ResponseClass responseClass=new ResponseClass();
        if (!Objects.equals(teacherJoinDTO.getTeacherPw(), TEACHER_KEY)){
            throw new LoginException();
        }
        return ResponseDTO.success(teacherService.Join(teacherJoinDTO.getName(),teacherJoinDTO.getPw()));
    }

    @GetMapping()
    public ResponseDTO AllTeacher(){
        return ResponseDTO.success(teacherService.AllTeacher());
    }
}
