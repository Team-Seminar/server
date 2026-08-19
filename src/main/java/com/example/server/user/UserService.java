package com.example.server.user;

import com.example.server.DTO.TokensDTO;
import com.example.server.DTO.UserJoinDTO;
import com.example.server.DTO.UserLoginDTO;
import com.example.server.global.security.JWT.TokenManager;
import com.example.server.global.security.error.exception.CustomException;
import com.example.server.global.security.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true, rollbackFor = CustomException.class, timeout = 60) //1분 이상 소요시 자동 롤백
public class UserService {

    final private PasswordEncoder passwordEncoder;

    final private UserRepository userRepository;
    private final TokenManager tokenManager;
    private final UserProperties userProperties;

    @Transactional
    public TokensDTO login(UserLoginDTO loginDTO) { //틀렸다면 위에서 에러를 던져 반환값까지 못가게 하는 방식
        User user = userRepository.findByName(loginDTO.getName())
                .orElseThrow(()->new CustomException(ErrorCode.NOT_ALLOW_LOGIN));

        if (!passwordEncoder.matches(loginDTO.getPw(), user.getPw())) { //pw가 틀렸다면
            throw new CustomException(ErrorCode.NOT_ALLOW_LOGIN);
        }
        if(user.getRole().equals(UserRole.TEACHER)) {
            return tokenManager.createToken(user.getId().toString(), "ROLE_TEACHER");
        }
        return tokenManager.createToken(user.getId().toString(), "ROLE_STUDENT");
    }

    @Transactional
    public String join(UserJoinDTO joinDTO){
        UserRole role=UserRole.STUDENT;
        if(joinDTO.getTeacherPw().equals(userProperties.getTeacherPw())){
            role=UserRole.TEACHER;
        }
        if (joinDTO.getPw().equals(joinDTO.getCheckPw())){
            throw new CustomException(ErrorCode.NOT_EQUALS_PASSWORD);
        }
        User user= User.builder()
                .name(joinDTO.getName())
                .pw(passwordEncoder.encode(joinDTO.getPw()))
                .role(role)
                .build();
        userRepository.save(user);
        return "회원가입 성공";
    }

    public List<User> AllUser(){
        return userRepository.findAll();
    }
}
