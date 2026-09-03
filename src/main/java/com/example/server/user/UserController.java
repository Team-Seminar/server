package com.example.server.user;

import com.example.server.DTO.ResponseDTO;
import com.example.server.DTO.TeacherJoinDTO;
import com.example.server.DTO.UserJoinDTO;
import com.example.server.DTO.UserLoginDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/users")
@RestController
@RequiredArgsConstructor
public class UserController {
    final private UserService UserService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO login(@RequestBody UserLoginDTO userLoginDTO){
        return ResponseDTO.success(UserService.login(userLoginDTO));
    }


    @PostMapping("/join/student")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO studentJoin(@RequestBody UserJoinDTO userJoinDTO){
        return ResponseDTO.success(UserService.join(userJoinDTO));
    }

    @PostMapping("/join/teacher")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO teacherJoin(@RequestBody TeacherJoinDTO teacherJoinDTO){
        return ResponseDTO.success(UserService.teacherJoin(teacherJoinDTO));
    }

}
