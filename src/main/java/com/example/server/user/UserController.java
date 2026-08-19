package com.example.server.user;

import com.example.server.DTO.ResponseDTO;
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
    public ResponseDTO Login(@RequestBody UserLoginDTO userLoginDTO){
        return ResponseDTO.success(UserService.login(userLoginDTO));
    }


    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO Join(@RequestBody UserJoinDTO userJoinDTO){
        return ResponseDTO.success(UserService.join(userJoinDTO));
    }

    @GetMapping()
    public ResponseDTO AllTeacher(){
        return ResponseDTO.success(UserService.AllUser());
    }
}
