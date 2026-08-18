package com.example.server.teacher;

import com.example.server.DTO.TokensDTO;
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
public class TeacherService {

    final private PasswordEncoder passwordEncoder;

    final private TeacherRepository teacherRepository;
    private final TokenManager tokenManager;

    @Transactional
    public TokensDTO login(String name, String pw) { //틀렸다면 위에서 에러를 던져 반환값까지 못가게 하는 방식
        Teacher teacher = teacherRepository.findByName(name)
                .orElseThrow(()->new CustomException(ErrorCode.NOT_ALLOW_LOGIN));

        if (!passwordEncoder.matches(pw, teacher.getPw())) { //pw가 틀렸다면
            throw new CustomException(ErrorCode.NOT_ALLOW_LOGIN);
        }

        return tokenManager.createToken(teacher.getId().toString(),"ROLE_TEACHER");
    }

    @Transactional
    public String Join(String name, String pw){

        Teacher teacher= Teacher.builder()
                .name(name)
                .pw(passwordEncoder.encode(pw))
                .build();
        teacherRepository.save(teacher);
        return "회원가입 성공";
    }

    public List<Teacher> AllTeacher(){
        return teacherRepository.findAll();
    }
}
