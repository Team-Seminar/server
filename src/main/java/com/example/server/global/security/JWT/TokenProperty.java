package com.example.server.global.security.JWT;

import org.springframework.boot.context.properties.ConfigurationProperties;

//jwt관련 환경변수를 불러오는 프로퍼티
@ConfigurationProperties("jwt")
public record TokenProperty (
        String secretKey
){
}
