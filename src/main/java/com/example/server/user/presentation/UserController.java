package com.example.server.user.presentation;

import com.example.server.DTO.ResponseDTO;
import com.example.server.DTO.StudentJoinDTO;
import com.example.server.DTO.TokensDTO;
import com.example.server.DTO.UserLoginDTO;
import com.example.server.user.UserRole;
import com.example.server.user.UserService;
import com.example.server.user.presentation.dto.UserDTO;
import com.example.server.user.presentation.dto.UserJoinDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseDTO<UserDTO> getMe(
            @RequestHeader("Authorization") String token
    ){
        return ResponseDTO.<UserDTO>success(userService.getMe(token));
    }

    @PostMapping("/login")
    public ResponseDTO<TokensDTO> login(
            @RequestBody UserLoginDTO loginDTO
    ){
        return ResponseDTO.<TokensDTO>success(userService.login(loginDTO));
    }

    @PostMapping("/refresh")
    public ResponseDTO<TokensDTO> refresh(
            @RequestBody TokensDTO tokensDTO
    ){
        return ResponseDTO.<TokensDTO>success(userService.refresh(tokensDTO));
    }

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.CREATED)
    public void join(
            @RequestBody UserJoinDTO joinDTO
    ){
        userService.join(joinDTO, UserRole.TEACHER);
    }
}
