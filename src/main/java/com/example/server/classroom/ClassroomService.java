package com.example.server.classroom;

import com.example.server.request.Request;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassroomService {
    final private ClassroomRepository classroomRepository;
    public Classroom classroomGet(Long classId){
        return classroomRepository.findById(classId).orElseThrow();
    }
    public List<Classroom> classroomGetAll(){
        return classroomRepository.findAll();
    }

    public Classroom classroomCreate(String name){
        Classroom classroom=new Classroom(name);
        System.out.println(classroom.getName());
        classroomRepository.save(classroom);
        return classroom;
    }
}
