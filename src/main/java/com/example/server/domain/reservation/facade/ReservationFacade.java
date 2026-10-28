package com.example.server.domain.reservation.facade;

import com.example.server.domain.reservation.ReservationService;
import com.example.server.domain.reservation.facade.dto.ReservationCreateDTO;
import com.example.server.domain.reservation.presentation.dto.BookingDTO;
import com.example.server.domain.reservationSlot.ReservationSlotService;
import com.example.server.domain.reservationSlot.presentation.dto.CreateSlotDTO;
import com.example.server.domain.student.StudentService;
import com.example.server.global.security.JWT.TokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ReservationFacade {
    private final ReservationService reservationService;
    private final ReservationSlotService slotService;
    private final StudentService studentService;
    private final TokenManager tokenManager;

    //예약 facade
    public String booking(
            String token,
            BookingDTO dto
    ){
        //필요 값만 얻어서 활용
        Map<String,String> data = reservationService.reservationCreate(
                ReservationCreateDTO.builder()
                        .classroomId(dto.classroomId())
                        .reason(dto.reason())
                        .build()
        );

        String code = data.get("code");
        long reservationId = Long.parseLong(data.get("id"));

        //예약자의 학번을 추가
        dto.schoolNumbers().add(studentService.findUser(tokenManager.getSubject(token)).getSchoolNumber());
        //학생당 각 교시에 엔티티 생성
        dto.schoolNumbers().forEach((number)->{
            dto.periods().forEach((period)->{
                //교시별 예약 생성
                slotService.createSlot(
                        CreateSlotDTO.builder()
                                .schoolNumber(number)
                                .reservationId(reservationId)
                                .period(period)
                                .build()
                );
            });
        });

        return code;
    }
}
