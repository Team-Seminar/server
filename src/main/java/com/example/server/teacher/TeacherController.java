package com.example.server.teacher;

import com.example.server.global.ResponseClass;
import com.example.server.DTO.TeacherJoinDTO;
import com.example.server.DTO.TeacherLoginDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequestMapping("api/v1/teachers")
@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500") //프론트 문 열음
public class TeacherController {
    final private TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }
    @PostMapping("/login")
    public ResponseEntity<?> Login(@RequestBody TeacherLoginDTO teacherLoginDTO){
        ResponseClass responseClass=new ResponseClass();
        String token=teacherService.login(teacherLoginDTO.getName(),teacherLoginDTO.getPw());
        responseClass.addData("Authorization",token);
        return responseClass.responseReturn();
    }

    @PostMapping()
    public ResponseEntity<?> Join(@RequestBody TeacherJoinDTO teacherJoinDTO){
        ResponseClass responseClass=new ResponseClass();
        if (!Objects.equals(teacherJoinDTO.getTeacherPw(),"iamteacher")){
            responseClass.addData("message","선생님이 아닙니다.");
        }
        else{
            responseClass.addData("message", teacherService.Join(teacherJoinDTO.getName(),teacherJoinDTO.getPw()));
        }
        return responseClass.responseReturn();
    }
}
