package com.example.server.domain.reservation.presentation;

import com.example.server.domain.reservation.facade.ReservationFacade;
import com.example.server.domain.reservation.presentation.dto.ReadReservationDTO;
import com.example.server.domain.reservation.facade.dto.BookingDTO;
import com.example.server.domain.reservation.presentation.dto.ReservationUseDTO;
import com.example.server.global.common.dto.ResponseDTO;
import com.example.server.domain.classroom.ClassroomService;
import com.example.server.global.error.exception.CustomException;
import com.example.server.global.error.exception.ErrorCode;
import com.example.server.domain.reservation.ReservationService;
import com.example.server.domain.reservation.ReservationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.time.Clock;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import com.example.server.domain.reservationSlot.ReservationSlotRepository;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservation")
public class ReservationController {
    final private ReservationService reservationService;
    private final ReservationFacade reservationFacade;
    final private ClassroomService classroomService;
    private final Clock clock;
    private final ReservationSlotRepository slotRepository;
    //생성
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<String> RequestCreate(
            @RequestHeader("Authorization") String token,
            @RequestBody BookingDTO dto
    ){
        LocalTime nowTime=LocalTime.now(clock);
        if (
                nowTime.isAfter(LocalTime.of(8,0)) &&
                nowTime.isBefore(LocalTime.of(16,20))
        ){
            throw new CustomException(ErrorCode.IS_TIME_NOT);
        }
        return ResponseDTO.<String>success(
                reservationFacade.booking(token, dto)
        );
    }

    //읽기
    //상세
    @GetMapping("/{id}")
    public ResponseDTO<ReadReservationDTO> RequestGet(@PathVariable Long id){
        return ResponseDTO.<ReadReservationDTO>success(reservationService.reservationGet(id));
    }

    //전체
    @GetMapping()
    public ResponseDTO<List<ReadReservationDTO>> RequestGetAll(@RequestParam(required = false) Long classroomId){
        return ResponseDTO.<List<ReadReservationDTO>>success(reservationService.reservationGetAll(classroomId));
    }

    //수정
    //예약 승인&거절 api
    @PreAuthorize("hasRole('TEACHER')")
    @PatchMapping("/{classroomId}/{status}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void UpdateStatus(
            @PathVariable Long classroomId,
            @PathVariable ReservationStatus status
    ){
        reservationService.reservationUpdate(classroomId, status);
    }

    //임베디드 교실 이용으로 상태 변경을 위한 api
    @PatchMapping("/{classroomId}/use")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void use(
            @PathVariable Long classroomId,
            @RequestBody ReservationUseDTO useDTO
    ){
        reservationService.reservationUse(classroomId, useDTO);
    }

    //삭제
    //예약 취소시 실행
    @DeleteMapping("/{reservationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void DeleteRequest(@PathVariable Long reservationId, Authentication authentication){
        if (authentication == null || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            throw new CustomException(ErrorCode.NOT_ABLE_STUDENT);
        }
        boolean teacher = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_TEACHER"));
        boolean student = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_STUDENT"));
        if (!teacher && !(student && slotRepository.existsByReservation_IdAndStudent_User_Id(
                reservationId, authentication.getName()))) {
            throw new CustomException(ErrorCode.NOT_ABLE_STUDENT);
        }
        reservationService.reservationDelete(reservationId);
    }
}
