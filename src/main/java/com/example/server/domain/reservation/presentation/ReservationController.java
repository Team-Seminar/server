package com.example.server.domain.reservation.presentation;

import com.example.server.domain.reservation.presentation.dto.ReadReservationDTO;
import com.example.server.domain.reservation.presentation.dto.ReservationCreateDTO;
import com.example.server.domain.reservation.presentation.dto.ReservationUseDTO;
import com.example.server.global.common.dto.ResponseDTO;
import com.example.server.domain.classroom.ClassroomService;
import com.example.server.global.security.error.exception.CustomException;
import com.example.server.global.security.error.exception.ErrorCode;
import com.example.server.domain.reservation.ReservationService;
import com.example.server.domain.reservation.ReservationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<String> RequestCreate(
            @RequestHeader("Authorization") String token,
            @RequestBody ReservationCreateDTO reservationCreateDTO
    ){
        LocalTime nowTime=LocalTime.now();
        if (
                nowTime.isAfter(LocalTime.of(8,0)) &&
                nowTime.isBefore(LocalTime.of(16,20))
        ){
            throw new CustomException(ErrorCode.IS_TIME_NOT);
        }
        return ResponseDTO.<String>success(
                reservationService.reservationCreate(reservationCreateDTO, token)
        );
    }

    //읽기
    @GetMapping("/{id}")
    public ResponseDTO<ReadReservationDTO> RequestGet(@PathVariable Long id){
        return ResponseDTO.<ReadReservationDTO>success(reservationService.reservationGet(id));
    }

    @GetMapping("/{classroomId}")
    public ResponseDTO<List<ReadReservationDTO>> getAllByClassroom (@PathVariable Long classroomId){
        return ResponseDTO.<List<ReadReservationDTO>>success(reservationService.reservationGetAll(classroomService.classroomGet(classroomId)));
    }

    @GetMapping()
    public ResponseDTO<List<ReadReservationDTO>> RequestGetAll(){
        return ResponseDTO.<List<ReadReservationDTO>>success(reservationService.reservationGetAll());
    }

    //수정
    @PreAuthorize("hasAuthority('TEACHER')")
    @PatchMapping("/Status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void UpdateStatus(
            @RequestParam Long id,
            @RequestParam ReservationStatus status
    ){
        reservationService.reservationUpdate(id, status);
    }

    @PatchMapping("/{classroomId}/use")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void use(
            @PathVariable Long classroomId,
            @RequestBody ReservationUseDTO useDTO
    ){
        reservationService.reservationUse(classroomId, useDTO);
    }

    //삭제
    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void DeleteRequest(@RequestParam Long id){
        reservationService.reservationDelete(id);
    }
}
