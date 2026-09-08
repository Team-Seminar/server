package com.example.server.student;

import com.example.server.DTO.StudentJoinDTO;
import com.example.server.DTO.TokensDTO;
import com.example.server.DTO.UserLoginDTO;
import com.example.server.global.security.JWT.TokenManager;
import com.example.server.global.security.error.exception.CustomException;
import com.example.server.global.security.error.exception.ErrorCode;
import com.example.server.user.User;
import com.example.server.user.UserRepository;
import com.example.server.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, rollbackFor = CustomException.class, timeout = 60) //1분 이상 소요시 자동 롤백
public class    StudentService {
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenManager tokenManager;

    @Transactional
    public String join(StudentJoinDTO dto){
        if (!dto.pw().equals(dto.checkPw())){
            throw new CustomException(ErrorCode.NOT_EQUALS_PASSWORD);
        }
        if (studentRepository.existsByLoginId(dto.loginId())){
            throw new CustomException(ErrorCode.IS_USE_NAME);
        }
        if (studentRepository.existsBySchoolNumber(dto.schoolNumber())){
            throw new CustomException(ErrorCode.OVERLAP_JOIN_STUDENT);
        }

        User user = User.builder()
                        .role(UserRole.STUDENT)
                        .loginId(dto.loginId())
                        .pw(dto.pw())
                        .build();
        userRepository.save(user);

        studentRepository.save(
                Student.builder()
                        .schoolNumber(dto.schoolNumber())
                        .user(user)
                        .build()
        );

        return "학생의 회원가입이 성공하였습니다";
    }
}
