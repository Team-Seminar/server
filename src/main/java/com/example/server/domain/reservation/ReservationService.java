package com.example.server.domain.reservation;

import com.example.server.domain.reservation.facade.dto.ReservationCreateDTO;
import com.example.server.domain.reservation.presentation.dto.ReadReservationDTO;
import com.example.server.domain.reservation.presentation.dto.BookingDTO;
import com.example.server.domain.reservation.presentation.dto.ReservationUseDTO;
import com.example.server.domain.classroom.Classroom;
import com.example.server.domain.classroom.ClassroomRepository;
import com.example.server.global.error.exception.CustomException;
import com.example.server.global.error.exception.ErrorCode;
import com.example.server.domain.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true, rollbackFor = CustomException.class, timeout = 60) //1분 이상 소요시 자동 롤백
public class ReservationService {
    final private ReservationRepository reservationRepository;
    private final ClassroomRepository classroomRepository;

    //읽기
    public ReadReservationDTO reservationGet(Long reservationId){
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(()->new CustomException(ErrorCode.RESERVATION_NOT_FOUND));
        return ReadReservationDTO.EntityToDTO(reservation);
    }
    public List<ReadReservationDTO> reservationGetAll(){
        List<Reservation> reservationList = reservationRepository.findAll();
        return reservationList.stream()
                .map(ReadReservationDTO::EntityToDTO)
                .toList();
    }
    public List<ReadReservationDTO> reservationGetAll(Classroom classroom){
        List<Reservation> reservationList = reservationRepository.findAllByClassroom(classroom);
        return reservationList.stream()
                .map(ReadReservationDTO::EntityToDTO)
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
                .reason(dto.reason())
                .classroom(
                        classroomRepository.findById(dto.tableId())
                                .orElseThrow(()->new CustomException(ErrorCode.CLASS_NOT_FOUND))
                )
                .password(randomCode)
                .build();
        //저장 및 랜덤으로 생성한 비밀번호 반환
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
        Reservation reservation = reservationRepository.findByClassroomAndPasswordAndStatus(classroom, useDTO.reservationPW(), ReservationStatus.ALLOW)
                .orElseThrow(()->new CustomException(ErrorCode.RESERVATION_NOT_FOUND));

        reservation.setStatus(ReservationStatus.USE);
    }
}
