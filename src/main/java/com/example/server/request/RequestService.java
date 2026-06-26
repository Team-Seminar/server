package com.example.server.request;

import com.example.server.classroom.Classroom;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RequestService {
    final private RequestRepository requestRepository;

    //읽기
    public Request requestGet(Long requestId){
        return requestRepository.findById(requestId).orElseThrow();
    }
    public List<Request> requestGetAll(){
        return requestRepository.findAll();
    }
    public List<Request> requestGet(Classroom classroom){
        return requestRepository.findAllByClassroom(classroom);
    }
    //생성
    public void requestCreate(int time, String name, String reason, Classroom classroom){
        Request request = new Request(time, reason, classroom, name);
        requestRepository.save(request);
    }

    //삭제
    public Request requestDelete(Long id){
        Request request = requestRepository.findById(id).orElseThrow();
        requestRepository.deleteById(id);
        return request;
    }

    //업데이트
    @Transactional
    public void requestUpdate(Long id, requestStatus status){
        Request request = requestRepository.findById(id).orElseThrow();
        request.updateStatus(status);
    }
}
