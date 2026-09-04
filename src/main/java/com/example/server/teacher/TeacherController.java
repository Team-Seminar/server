package com.example.server.teacher;

import com.example.server.DTO.ResponseDTO;
import com.example.server.DTO.UserLoginDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/teacher")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;

    public ResponseDTO login(
            @RequestBody UserLoginDTO loginDTO
    ){
        return ResponseDTO.success(teacherService.login(loginDTO));
    }
}
