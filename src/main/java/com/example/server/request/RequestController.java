package com.example.server.request;

import com.example.server.DTO.RequestCreateDTO;
import com.example.server.classroom.Classroom;
import com.example.server.classroom.ClassroomService;
import com.example.server.classroom.ClassroomStatus;
import com.example.server.global.ResponseClass;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PostMapping("/")
    public ResponseEntity<?> RequestCreate(@RequestBody RequestCreateDTO requestCreateDTO){
        Long tableId=requestCreateDTO.getTableId();
        String name=requestCreateDTO.getName();
        String reason=requestCreateDTO.getReason();
        int time=requestCreateDTO.getTime();

        ResponseClass responseClass=new ResponseClass();
        LocalTime nowTime=LocalTime.now();
        if (nowTime.getHour()<6 || nowTime.getHour()>18){
            return responseClass.massageReturn("예약 가능 시간이 아닙니다");
        }
        Classroom classroom =classroomService.classroomGet(tableId);
        if(classroom.getStatus() != ClassroomStatus.EMPTY){
            return responseClass.massageReturn("예약이 불가합니다");
        }
        requestService.requestCreate(time, name,  reason, classroom);
        return responseClass.massageReturn("예약 성공");
    }
    //읽기
    @GetMapping("/")
    public Request RequestGet(@RequestParam Long id){
        return requestService.requestGet(id);
    }

    @GetMapping("/All")
    public List<Request> RequestGetAll(){
        return requestService.requestGetAll();
    }

    //수정
    @PreAuthorize("hasAuthority('teacher')")
    @PatchMapping("/UpdateStatus")
    public void UpdateStatus(@RequestHeader("Authorization") String token, @RequestParam Long id, @RequestParam requestStatus status){
        requestService.requestUpdate(id, status);
    }

    //삭제
    @DeleteMapping("/Delete")
    public Request DeleteRequest(@RequestParam Long id){
        return requestService.requestDelete(id);
    }
}
