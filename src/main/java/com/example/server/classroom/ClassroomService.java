package com.example.server.classroom;

import com.example.server.DTO.ClassroomCreateDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ClassroomService {
    final private ClassroomRepository classroomRepository;

    //읽기
    public Classroom classroomGet(Long classId) {
        return classroomRepository.findById(classId).orElseThrow();
    }

    public List<Classroom> classroomGetAll() {
        return classroomRepository.findAll();
    }

    //생성
    public Classroom classroomCreate(ClassroomCreateDTO classroomCreateDTO) {
        Classroom classroom = Classroom.builder()
                .name(classroomCreateDTO.getName())
                .floor(classroomCreateDTO.getFloor())
                .status(ClassroomStatus.EMPTY)
                .build();
        classroomRepository.save(classroom);
        return classroom;
    }

    //삭제
    public void classroomDelete(Long classId) {
        Classroom classroom = classroomRepository.findById(classId).orElseThrow();
        classroomRepository.removeById(classId);
    }

    //수정
    @Transactional
    public void classroomUpdate(Long classId, ClassroomStatus status) {
        classroomRepository.findById(classId)
                .orElseThrow()
                .UpdateStatus(status);
    }
}
