package com.example.server.domain.reservationSlot;

import com.example.server.domain.reservation.Reservation;
import com.example.server.domain.reservation.repository.ReservationRepository;
import com.example.server.domain.reservationSlot.presentation.dto.CreateSlotDTO;
import com.example.server.domain.student.Student;
import com.example.server.domain.student.StudentRepository;
import com.example.server.global.error.exception.CustomException;
import com.example.server.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(timeout = 60, readOnly = true, rollbackFor = CustomException.class)
public class ReservationSlotService {
    private final ReservationSlotRepository reservationSlotRepository;
    private final ReservationRepository reservationRepository;
    private final StudentRepository studentRepository;

    //생성
    @Transactional
    public void createSlot(CreateSlotDTO dto){
        List<ReservationSlot> slotList = new ArrayList<>();

        Reservation reservation = reservationRepository.findById(dto.reservationId())
                .orElseThrow(()->new CustomException(ErrorCode.RESERVATION_NOT_FOUND));
        Student student = studentRepository.findBySchoolNumber(dto.schoolNumber())
                .orElseThrow(()->new CustomException(ErrorCode.USER_NOT_FOUND));

        dto.period().forEach((period)->{
            ReservationSlot slot = ReservationSlot.builder()
                    //예약 객체 불러오기
                    .reservation(reservation)
                    //학생 객체 불러오기
                    .student(student)
                    .period(period)
                    .build();
            slotList.add(slot);
        });

        reservationSlotRepository.saveAll(slotList);
    }
}
