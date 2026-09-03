package com.example.server.classroom;

import com.example.server.DTO.ClassroomCreateDTO;
import com.example.server.global.security.error.exception.CustomException;
import com.example.server.global.security.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, rollbackFor = CustomException.class, timeout = 60) //1분 이상 소요시 자동 롤백
public class ClassroomService {
    final private ClassroomRepository classroomRepository;

    //읽기
    public Classroom classroomGet(Long classId) {
        return classroomRepository
                .findById(classId)
                .orElseThrow(()->new CustomException(ErrorCode.CLASS_NOT_FOUND));
    }

    public List<Classroom> classroomGetAll() {
        return classroomRepository.findAll();
    }

}
