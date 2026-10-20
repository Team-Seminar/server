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

    @Scheduled(cron = "* * 22 * * *")
    @Transactional
    public void cleanReservation(){
        Long cnt=reservationRepository.deleteAllByCreateAtBefore(LocalDateTime.now());
        log.info("삭제된 예약의 개수: {}", cnt);
    }
}
