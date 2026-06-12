package com.example.server.classroom;

import com.example.server.request.Request;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassroomService {
    final private ClassroomRepository classroomRepository;

    //읽기
    public Classroom classroomGet(Long classId){
        return classroomRepository.findById(classId).orElseThrow();
    }
    public List<Classroom> classroomGetAll(){
        return classroomRepository.findAll();
    }
    //생성
    public Classroom classroomCreate(String name){
        Classroom classroom=new Classroom(name);
        System.out.println(classroom.getName());
        classroomRepository.save(classroom);
        return classroom;
    }

    //삭제
    public Classroom classroomDelete(Long classId){
        Classroom classroom = classroomRepository.findById(classId).orElseThrow();
        classroomRepository.removeById(classId);
        return classroom;
    }
}
