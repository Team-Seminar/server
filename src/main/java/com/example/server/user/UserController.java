package com.example.server.user;

import com.example.server.DTO.ResponseDTO;
import com.example.server.DTO.UserLoginDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseDTO login(
            @RequestBody UserLoginDTO loginDTO
    ){
        return ResponseDTO.success(userService.login(loginDTO));
    }
}
