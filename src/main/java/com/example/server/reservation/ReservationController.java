package com.example.server.reservation;

import com.example.server.DTO.ReservationCreateDTO;
import com.example.server.DTO.ResponseDTO;
import com.example.server.classroom.Classroom;
import com.example.server.classroom.ClassroomService;
import com.example.server.classroom.ClassroomStatus;
import com.example.server.global.security.error.exception.CustomException;
import com.example.server.global.security.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservation")
public class ReservationController {
    final private ReservationService reservationService;
    final private ClassroomService classroomService;
    //생성
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO RequestCreate(
            @RequestBody ReservationCreateDTO reservationCreateDTO
    ){

        LocalTime nowTime=LocalTime.now();
        if (nowTime.getHour()>6 && nowTime.getHour()<18){
            throw new CustomException(ErrorCode.IS_TIME_NOT);
        }

        return ResponseDTO.success(reservationService.reservationCreate(reservationCreateDTO));
    }
    //읽기
    @GetMapping("/{id}")
    public ResponseDTO RequestGet(@PathVariable Long id){
        return ResponseDTO.success(reservationService.reservationGet(id));
    }

    @GetMapping()
    public ResponseDTO RequestGetAll(){
        return ResponseDTO.success(reservationService.reservationGetAll());
    }

    //수정
    @PreAuthorize("hasAuthority('TEACHER')")
    @PatchMapping("/Status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void UpdateStatus(@RequestParam Long id, @RequestParam ReservationStatus status){
        reservationService.reservationUpdate(id, status);
    }

    //삭제
    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void DeleteRequest(@RequestParam Long id){
        reservationService.reservationDelete(id);
    }
}
