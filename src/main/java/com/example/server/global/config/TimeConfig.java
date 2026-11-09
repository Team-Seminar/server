package com.example.server.global.config;

import java.time.Clock;
import java.time.ZoneId;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TimeConfig {
    public static final String ZONE = "Asia/Seoul";

    @Bean
    public Clock clock() {
        return Clock.system(ZoneId.of(ZONE));
    }
}
