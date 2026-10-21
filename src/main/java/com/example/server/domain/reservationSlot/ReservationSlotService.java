package com.example.server.domain.reservationSlot;

import com.example.server.domain.reservationSlot.presentation.dto.CreateSlotDTO;
import com.example.server.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(timeout = 60, readOnly = true, rollbackFor = CustomException.class)
public class ReservationSlotService {
    private final ReservationSlotRepository reservationSlotRepository;

    //생성
    @Transactional
    public void createSlot(CreateSlotDTO createSlotDTO){
        reservationSlotRepository.save(
                ReservationSlot.builder()
                        .reservation(createSlotDTO.reservation())
                        .student(createSlotDTO.student())
                        .period(createSlotDTO.period())
                        .build()
        );
    }
}
