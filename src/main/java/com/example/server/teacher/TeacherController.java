package com.example.server.teacher;

import com.example.server.DTO.TeacherJoinDTO;
import com.example.server.DTO.TeacherLoginDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

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
        String token=teacherService.login(teacherLoginDTO.getName(),teacherLoginDTO.getPw());
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        return new ResponseEntity<>("Login Successful", headers, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Map<String, String>> Join(@RequestBody TeacherJoinDTO teacherJoinDTO){
        Map<String,String> response= new HashMap<>();
        if (!Objects.equals(teacherJoinDTO.getTeacherPw(),"iamteacher")){
            response.put("message","선생님이 아닙니다.");
            return ResponseEntity.ok(response);
        }
        response.put("message", teacherService.Join(teacherJoinDTO.getName(),teacherJoinDTO.getPw()));
        return ResponseEntity.ok(response);
    }
}
