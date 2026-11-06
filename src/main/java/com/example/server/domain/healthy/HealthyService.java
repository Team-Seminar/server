package com.example.server.domain.healthy;

import org.springframework.stereotype.Service;

@Service
public class HealthyService {

    public String Healthy(){
        return "서버가 살았습니다.";
    }
}
