package com.example.server.teacher;

import com.example.server.DTO.TokensDTO;
import com.example.server.DTO.UserLoginDTO;
import com.example.server.global.security.JWT.TokenManager;
import com.example.server.global.security.error.exception.CustomException;
import com.example.server.global.security.error.exception.ErrorCode;
import com.example.server.student.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(timeout = 60, readOnly = true, rollbackFor = CustomException.class)
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final TokenManager tokenManager;

    @Transactional
    public TokensDTO login(UserLoginDTO loginDTO) { //틀렸다면 위에서 에러를 던져 반환값까지 못가게 하는 방식
        Teacher teacher = teacherRepository.findByLoginIdAndPw(loginDTO.getLoginId(), loginDTO.getPw()).orElseThrow(()->new CustomException(ErrorCode.NOT_ALLOW_LOGIN));
        return tokenManager.createToken(teacher.getId().toString(), teacher.getRole().toStr());
    }
}
