package com.example.server.student;

import com.example.server.DTO.StudentJoinDTO;
import com.example.server.global.security.error.exception.CustomException;
import com.example.server.global.security.error.exception.ErrorCode;
import com.example.server.user.UserRepository;
import com.example.server.user.UserRole;
import com.example.server.user.UserService;
import com.example.server.user.presentation.dto.UserJoinDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, rollbackFor = CustomException.class, timeout = 60) //1분 이상 소요시 자동 롤백
public class    StudentService {
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final UserService userService;

    @Transactional
    public String join(StudentJoinDTO dto){
        if (studentRepository.existsBySchoolNumber(dto.schoolNumber())){
            throw new CustomException(ErrorCode.OVERLAP_JOIN_STUDENT);
        }

        userService.join(
                new UserJoinDTO(dto.loginId(), dto.pw(), dto.checkPw()),
                UserRole.STUDENT
        );

        studentRepository.save(
                Student.builder()
                        .schoolNumber(dto.schoolNumber())
                        .user(userRepository.findByLoginId(dto.loginId())
                                .orElseThrow(()->new CustomException(ErrorCode.USER_NOT_FOUND))
                        )
                        .build()
        );

        return "학생의 회원가입이 성공하였습니다";
    }
}
