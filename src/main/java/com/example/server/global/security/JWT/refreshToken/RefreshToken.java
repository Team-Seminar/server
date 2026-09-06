package com.example.server.global.security.JWT.refreshToken;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
@RedisHash(value = "RT") // Redis Key의 Prefix가 됨 (예: RT:userId)
public class RefreshToken {

    @Id
    private String subject; // Key 식별자 (실제 Key: RT:{userId})

    private String refreshToken;

    @TimeToLive // 초(second) 단위로 TTL 지정 (예: 7일 = 604,800초)
    @Builder.Default
    private Long expiration = 60L*60*24*7;
}
