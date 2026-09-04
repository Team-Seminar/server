package com.example.server.student;

import com.example.server.DTO.ResponseDTO;
import com.example.server.DTO.StudentJoinDTO;
import com.example.server.DTO.UserLoginDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/student")
public class StudentController {
    private final StudentService studentService;

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO join(
            @RequestBody StudentJoinDTO joinDTO
    ){
        return ResponseDTO.success(studentService.join(joinDTO));
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO login(
            @RequestBody UserLoginDTO userLoginDTO
    ){
        return ResponseDTO.success(studentService.login(userLoginDTO));
    }
}
