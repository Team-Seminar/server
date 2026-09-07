package com.example.server.reservation;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class ReservationClear {
    private final ReservationRepository reservationRepository;

    @Scheduled(cron = "* * 22 * * *")
    @Transactional
    public void cleanReservation(){
        reservationRepository.deleteAllByCreateAtBefore(LocalDateTime.now());
    }
}
