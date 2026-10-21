package com.example.server.domain.student;

import com.example.server.domain.student.presentation.dto.StudentJoinDTO;
import com.example.server.global.error.exception.CustomException;
import com.example.server.global.error.exception.ErrorCode;
import com.example.server.domain.user.UserRepository;
import com.example.server.domain.user.UserRole;
import com.example.server.domain.user.UserService;
import com.example.server.domain.user.presentation.dto.UserJoinDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

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

    public Student findUser(UUID id){
        return studentRepository.findByUser_Id(id)
                .orElseThrow(()->new CustomException(ErrorCode.USER_NOT_FOUND));
    }
}
