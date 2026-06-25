package com.example.server.teacher;

import com.example.server.DTO.TeacherJoinDTO;
import com.example.server.DTO.TeacherLoginDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> Login(@RequestBody TeacherLoginDTO teacherLoginDTO){
        String token=teacherService.login(teacherLoginDTO.getName(),teacherLoginDTO.getPw());
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        return new ResponseEntity<>("Login Successful", headers, HttpStatus.OK);
    }

    @PostMapping()
    public String Join(@RequestBody TeacherJoinDTO teacherJoinDTO){
        if (!teacherJoinDTO.getTeacherPw().equals("iamteacher")){
            return "선생님이 아닙니다.";
        }
        return teacherService.Join(teacherJoinDTO.getName(),teacherJoinDTO.getPw());
    }
}
