package com.example.server.global.security.JWT;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("jwt")
public record TokenProperty (
        String secretKey
){
}
