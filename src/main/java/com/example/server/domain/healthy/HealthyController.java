package com.example.server.domain.healthy;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HealthyController {
    private final HealthyService healthyService;

    //서버 건강 여부 확인 api
    @GetMapping
    public String Healthy(){
        return healthyService.Healthy();
    }
}
