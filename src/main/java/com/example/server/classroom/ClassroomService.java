package com.example.server.classroom;

import com.example.server.request.Request;
import com.example.server.request.RequestService;
import com.example.server.request.requestStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ClassroomService {
    final private ClassroomRepository classroomRepository;
    final private RequestService requestService;

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
    //수정
    public void classroomUpdate(Long classId, ClassroomStatus status){
        classroomRepository.findById(classId)
                .orElseThrow()
                .UpdateStatus(status);
        if (status==ClassroomStatus.use){
            List<Request> requestList=requestService.requestGet(classroomGet(classId));
            for (int i = 0; i < requestList.size(); i++) {
                requestService.requestUpdate(requestList.get(i).getId(), requestStatus.REFUSE);
            }
        }
    }
}
