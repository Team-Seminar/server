package com.example.server.reservation;

import com.example.server.DTO.ReservationCreateDTO;
import com.example.server.classroom.Classroom;
import com.example.server.classroom.ClassroomRepository;
import com.example.server.classroom.ClassroomService;
import com.example.server.global.security.error.exception.CustomException;
import com.example.server.global.security.error.exception.ErrorCode;
import com.example.server.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true, rollbackFor = CustomException.class, timeout = 60) //1분 이상 소요시 자동 롤백
public class ReservationService {
    final private ReservationRepository reservationRepository;
    private final ClassroomRepository classroomRepository;
    private final UserRepository userRepository;
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
    public String reservationCreate(ReservationCreateDTO dto){
        int num = ThreadLocalRandom.current().nextInt(0, 10000);
        String randomCode = String.format("%04d", num);
        Reservation reservation = Reservation.builder()
                .startAt(dto.getStartAt())
                .endAt(dto.getEndAt())
                .reason(dto.getReason())
                .classroom(classroomRepository.findById(dto.getTableId()).orElseThrow(()->new CustomException(ErrorCode.CLASS_NOT_FOUND)))
                .groups(dto.getName().stream()
                        .map((userName)->{
                            return userRepository.findByName(userName).orElseThrow(()->new CustomException(ErrorCode.TEACHER_NOT_FOUND));
                        })
                        .collect(Collectors.toList())
                )
                .password(randomCode)
                .build();
        reservationRepository.save(reservation);
        return randomCode;
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
        if (status == ReservationStatus.ALLOW) {
            reservationRepository.updateRefuse(reservation.startAt, reservation.endAt);
        }
        reservation.updateStatus(status);
    }
}
