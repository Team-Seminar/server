package com.example.server.reservation;

import com.example.server.classroom.Classroom;
import com.example.server.classroom.ClassroomService;
import com.example.server.classroom.ClassroomStatus;
import com.example.server.global.security.error.exception.CustomException;
import com.example.server.global.security.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true, rollbackFor = CustomException.class, timeout = 60) //1분 이상 소요시 자동 롤백
public class ReservationService {
    final private ReservationRepository reservationRepository;
    final private ClassroomService classroomService;

    //읽기
    public Reservation reservationGet(Long reservationId){
        return reservationRepository.findById(reservationId).orElseThrow(()->new CustomException(ErrorCode.RESERVATION_NOT_FOUND));
    }
    public List<Reservation> reservationGetAll(){
        return reservationRepository.findAll();
    }
    public List<Reservation> reservationGet(Classroom classroom){
        return reservationRepository.findAllByClassroom(classroom);
    }
    //생성
    @Transactional
    public Long reservationCreate(int time, String name, String reason, Classroom classroom){
        Reservation reservation = Reservation.builder()
                .time(time)
                .reason(reason)
                .classroom(classroom)
                .name(name)
                .build();
        reservationRepository.save(reservation);
        return reservation.getId();
    }

    //삭제
    @Transactional
    public void reservationDelete(Long id){
        reservationRepository.deleteById(id);
    }

    //업데이트
    @Transactional
    public void reservationUpdate(Long id, reservationStatus status){
        Reservation reservation = reservationRepository
                .findById(id)
                .orElseThrow(()->new CustomException(ErrorCode.RESERVATION_NOT_FOUND));
        if (status== reservationStatus.ALLOW) {
            for (Reservation temp : reservationGet(reservation.getClassroom())) {
                temp.updateStatus(reservationStatus.REFUSE);
            }
            classroomService.classroomUpdate(reservation.getClassroom().getId(), ClassroomStatus.ONE);
        }
        reservation.updateStatus(status);
    }
}
