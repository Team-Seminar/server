package com.example.server.student;

import com.example.server.DTO.StudentJoinDTO;
import com.example.server.DTO.TokensDTO;
import com.example.server.DTO.UserLoginDTO;
import com.example.server.global.security.JWT.TokenManager;
import com.example.server.global.security.error.exception.CustomException;
import com.example.server.global.security.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, rollbackFor = CustomException.class, timeout = 60) //1분 이상 소요시 자동 롤백
public class    StudentService {
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

        studentRepository.save(
                Student.builder()
                        .schoolNumber(dto.schoolNumber())
                        .loginId(dto.loginId())
                        .pw(dto.pw())
                        .build()
        );

        return "학생의 회원가입이 성공하였습니다";
    }


    @Transactional
    public TokensDTO login(UserLoginDTO loginDTO) { //틀렸다면 위에서 에러를 던져 반환값까지 못가게 하는 방식
        Student student = studentRepository.findByLoginId(loginDTO.getLoginId())
                .orElseThrow(()->new CustomException(ErrorCode.NOT_ALLOW_LOGIN));

        if (!passwordEncoder.matches(loginDTO.getPw(), student.getPw())) { //pw가 틀렸다면
            throw new CustomException(ErrorCode.NOT_ALLOW_LOGIN);
        }
        return tokenManager.createToken(student.getId().toString(), student.getRole().toStr());
    }
}
