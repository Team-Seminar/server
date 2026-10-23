package com.example.server.domain.student.presentation;

import com.example.server.domain.student.presentation.dto.StudentJoinDTO;
import com.example.server.global.common.dto.ResponseDTO;
import com.example.server.domain.student.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/students")
public class StudentController {
    private final StudentService studentService;

    //학생 회원가입 api
    @PostMapping("/join")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<String> join(
            @RequestBody StudentJoinDTO joinDTO
    ){
        return ResponseDTO.<String>success(studentService.join(joinDTO));
    }

}
