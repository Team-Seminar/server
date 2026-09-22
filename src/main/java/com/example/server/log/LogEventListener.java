package com.example.server.log;

import com.example.server.log.dto.LogEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class LogEventListener {
    private final LogRepository logRepository;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleLogEvent(LogEvent logEvent){
        LogEntity log = LogEntity.builder()
                .content(logEvent.content())
                .logStatus(logEvent.status())
                .build();
        logRepository.save(log);
    }
}
