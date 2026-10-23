package com.example.server.domain.reservation.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class ReservationClear {
    private final ReservationRepository reservationRepository;

    //매일 저녁 10시를 기준으로 이전에 생성된 예약 전체 삭제
    //즉 데이터베이스에는 그날 생성된 엔티티만 존재하게 됨
    @Scheduled(cron = "* * 22 * * *")
    @Transactional
    public void cleanReservation(){
        Long cnt=reservationRepository.deleteAllByCreateAtBefore(LocalDateTime.now());
        log.info("삭제된 예약의 개수: {}", cnt);
    }
}
