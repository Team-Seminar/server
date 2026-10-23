package com.example.server.domain.user.presentation;

import com.example.server.global.common.dto.ResponseDTO;
import com.example.server.global.common.dto.TokensDTO;
import com.example.server.domain.user.presentation.dto.UserLoginDTO;
import com.example.server.domain.user.UserRole;
import com.example.server.domain.user.UserService;
import com.example.server.domain.user.presentation.dto.UserDTO;
import com.example.server.domain.user.presentation.dto.UserJoinDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    //본인 정보 조회
    @GetMapping("/me")
    public ResponseDTO<UserDTO> getMe(
            @RequestHeader("Authorization") String token
    ){
        return ResponseDTO.<UserDTO>success(userService.getMe(token));
    }

    //로그인
    @PostMapping("/login")
    public ResponseDTO<TokensDTO> login(
            @RequestBody UserLoginDTO loginDTO
    ){
        return ResponseDTO.<TokensDTO>success(userService.login(loginDTO));
    }

    //토큰 리프레쉬
    @PostMapping("/refresh")
    public ResponseDTO<TokensDTO> refresh(
            @RequestBody TokensDTO tokensDTO
    ){
        return ResponseDTO.<TokensDTO>success(userService.refresh(tokensDTO));
    }

    //회원가입
    @PostMapping("/join")
    @ResponseStatus(HttpStatus.CREATED)
    public void join(
            @RequestBody UserJoinDTO joinDTO
    ){
        userService.join(joinDTO, UserRole.TEACHER);
    }
}
