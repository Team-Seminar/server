package com.example.server.reservation;

import com.example.server.classroom.Classroom;
import com.example.server.classroom.ClassroomService;
import com.example.server.classroom.ClassroomStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReservationService {
    final private ReservationRepository reservationRepository;
    final private ClassroomService classroomService;

    //읽기
    public Reservation reservationGet(Long reservationId){
        return reservationRepository.findById(reservationId).orElseThrow();
    }
    public List<Reservation> reservationGetAll(){
        return reservationRepository.findAll();
    }
    public List<Reservation> reservationGet(Classroom classroom){
        return reservationRepository.findAllByClassroom(classroom);
    }
    //생성
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
    public void reservationDelete(Long id){
        Reservation reservation = reservationRepository.findById(id).orElseThrow();
        reservationRepository.deleteById(id);
    }

    //업데이트
    @Transactional
    public void reservationUpdate(Long id, reservationStatus status){
        Reservation reservation = reservationRepository.findById(id).orElseThrow();
        if (status== reservationStatus.ALLOW) {
            for (Reservation temp : reservationGet(reservation.getClassroom())) {
                temp.updateStatus(reservationStatus.REFUSE);
            }
            classroomService.classroomUpdate(reservation.getClassroom().getId(), ClassroomStatus.ONE);
        }
        reservation.updateStatus(status);
    }
}
