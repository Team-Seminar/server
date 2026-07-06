package com.example.server.reservation;

import com.example.server.DTO.ReservationCreateDTO;
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
@RequestMapping("/api/v1/reservation")
public class ReservationController {
    final private ReservationService reservationService;
    final private ClassroomService classroomService;
    //생성
    @PostMapping()
    public ResponseEntity<?> RequestCreate(@RequestBody ReservationCreateDTO reservationCreateDTO){
        Long tableId=reservationCreateDTO.getTableId();
        String name=reservationCreateDTO.getName();
        String reason=reservationCreateDTO.getReason();
        int time=reservationCreateDTO.getTime();

        ResponseClass responseClass=new ResponseClass();
        LocalTime nowTime=LocalTime.now();
        if (nowTime.getHour()>6 && nowTime.getHour()<18){
            return responseClass.massageReturn("예약 가능 시간이 아닙니다");
        }
        Classroom classroom =classroomService.classroomGet(tableId);
        if(classroom.getStatus() != ClassroomStatus.EMPTY){
            return responseClass.massageReturn("예약이 불가합니다");
        }

        return responseClass.oneResponseReturn("data", reservationService.reservationCreate(time, name,  reason, classroom));
    }
    //읽기
    @GetMapping("/{id}")
    public Reservation RequestGet(@PathVariable Long id){
        return reservationService.reservationGet(id);
    }

    @GetMapping()
    public List<Reservation> RequestGetAll(){
        return reservationService.reservationGetAll();
    }

    //수정
    @PreAuthorize("hasAuthority('teacher')")
    @PatchMapping("/Status")
    public void UpdateStatus(@RequestParam Long id, @RequestParam reservationStatus status){
        reservationService.reservationUpdate(id, status);
    }

    //삭제
    @DeleteMapping()
    public Reservation DeleteRequest(@RequestParam Long id){
        return reservationService.reservationDelete(id);
    }
}
