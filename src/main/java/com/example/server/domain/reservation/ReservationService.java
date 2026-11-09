package com.example.server.domain.reservation;

import com.example.server.domain.reservation.presentation.dto.ReservationCreateDTO;
import com.example.server.domain.reservation.presentation.dto.ReadReservationDTO;
import com.example.server.domain.reservation.presentation.dto.ReservationUseDTO;
import com.example.server.domain.classroom.Classroom;
import com.example.server.domain.classroom.ClassroomRepository;
import com.example.server.domain.reservationSlot.ReservationSlotRepository;
import com.example.server.global.error.exception.CustomException;
import com.example.server.global.error.exception.ErrorCode;
import com.example.server.domain.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true, rollbackFor = CustomException.class, timeout = 60) //1분 이상 소요시 자동 롤백
public class ReservationService {
    final private ReservationRepository reservationRepository;
    private final ClassroomRepository classroomRepository;
    private final ReservationSlotRepository slotRepository;

    private final Clock clock;

    //읽기
    //상세 조회
    public ReadReservationDTO reservationGet(Long reservationId){
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(()->new CustomException(ErrorCode.RESERVATION_NOT_FOUND));
        return ReadReservationDTO.EntityToDTO(reservation, slotRepository.findPeriodsByReservation_Id(reservationId));
    }

    //전체조회
    public List<ReadReservationDTO> reservationGetAll(Long classroomId){
        if (classroomId==null||classroomId<0){
            return reservationRepository.findAll().stream()
                    .map(reservation -> ReadReservationDTO.EntityToDTO(
                            reservation,
                            slotRepository.findPeriodsByReservation_Id(reservation.getId())
                    ))
                    .toList();
        }
        return reservationRepository.findAllByClassroom_Id(classroomId).stream()
                    .map(reservation -> ReadReservationDTO.EntityToDTO(
                            reservation,
                            slotRepository.findPeriodsByReservation_Id(reservation.getId())
                    ))
                    .toList();
    }

    //생성
    @Transactional
    public Map<String,String> reservationCreate(ReservationCreateDTO dto){
        //4자리수 비밀번호 생성
        int num = ThreadLocalRandom.current().nextInt(0, 10000);
        String randomCode = String.format("%04d", num);
        while(reservationRepository.existsByPassword(randomCode)){ //중복 제외
            num=ThreadLocalRandom.current().nextInt(0, 10000);
            randomCode = String.format("%04d", num);
        }
        //예약 생성
        Reservation reservation = Reservation.builder()
                .createAt(LocalDateTime.now(clock))
                .reason(dto.reason())
                .classroom(
                        classroomRepository.findById(dto.classroomId())
                                .orElseThrow(()->new CustomException(ErrorCode.CLASS_NOT_FOUND))
                )
                .password(randomCode)
                .build();
        //저장 및 랜덤으로 생성한 비밀번호과 예약id 반환
        reservationRepository.save(reservation);
        return Map.of(
                "code", randomCode,
                "id", reservation.getId().toString()
        );
    }

    //삭제
    @Transactional
    public void reservationDelete(Long id){
        reservationRepository.deleteById(id);
    }

    //업데이트
    @Transactional
    public void reservationUpdate(Long id, ReservationStatus status){
        Reservation reservation = reservationRepository
                .findById(id)
                .orElseThrow(()->new CustomException(ErrorCode.RESERVATION_NOT_FOUND));
//        if (status == ReservationStatus.ALLOW) {
//            reservationRepository.updateRefuse(reservation.startAt, reservation.endAt);
//        }
        reservation.setStatus(status);
    }

    @Transactional
    public void reservationUse(Long classroomId, ReservationUseDTO useDTO){
        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(()->new CustomException(ErrorCode.CLASS_NOT_FOUND));
        Reservation reservation = reservationRepository.findByClassroomAndPassword(classroom, useDTO.reservationPW())
                .orElseThrow(()->new CustomException(ErrorCode.RESERVATION_NOT_FOUND));
        if (!reservation.getStatus().equals(ReservationStatus.ALLOW)){
            throw new CustomException(ErrorCode.RESERVATION_NOT_ALLOW);
        }

        reservation.setStatus(ReservationStatus.USE);
    }
}
