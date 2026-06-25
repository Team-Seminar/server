package com.example.server.request;

import com.example.server.classroom.Classroom;
import com.example.server.classroom.ClassroomService;
import com.example.server.classroom.ClassroomStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Request")
public class RequestController {
    final private RequestService requestService;
    final private ClassroomService classroomService;
    //생성
    @GetMapping("/Create")
    public String RequestCreate(@RequestParam Long tableId,@RequestParam String name, @RequestParam String reason, @RequestParam int time){
        LocalTime nowTime=LocalTime.now();
        if (nowTime.getHour()<6 || nowTime.getHour()>18){
            return "예약 가능 시간이 아닙니다";
        }
        Classroom classroom =classroomService.classroomGet(tableId);
        if(classroom.getStatus() != ClassroomStatus.empty){
            return "예약이 불가합니다";
        }
        requestService.requestCreate(time, name,  reason, classroom);
        return "예약 성공";
    }
    //읽기
    @GetMapping("/Get")
    public Request RequestGet(@RequestParam Long id){
        return requestService.requestGet(id);
    }

    @GetMapping("/GetAll")
    public List<Request> RequestGetAll(){
        return requestService.requestGetAll();
    }

    //수정
    @GetMapping("/UpdateStatus")
    public void UpdateStatus(@RequestHeader("Authorization") String token, @RequestParam Long id, @RequestParam requestStatus status){
        requestService.requestUpdate(id, status);
    }

    //삭제
    @GetMapping("/Delete")
    public Request DeleteRequest(@RequestParam Long id){
        return requestService.requestDelete(id);
    }
}
