package com.example.server.global.security.JWT;

import com.example.server.DTO.TokensDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;

//id는 subject에 저장
//role는 역할이나 권한


@Component
public class TokenManager {
    private final SecretKey SECRET_KEY;

    final static private Long VALID_TIME= 30 * 60 * 1000L; //토큰 허용 시간(30분)
    final static private Long REFRESH_VALID_TIME= 14*24*60 * 60 * 1000L; //토큰 허용 시간(30분)

    public TokenManager(TokenProperty tokenProperty){

        final String SECRET_KEY_STRING = tokenProperty.secretKey(); //보안 키
        if (SECRET_KEY_STRING == null) {
            throw new IllegalStateException("JWT Secret Key가 null입니다. application.yml 설정을 확인하세요.");
        }
        this.SECRET_KEY=Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String id, Long exp , Map<String, Object> tokenContent){
        Date now = new Date();
        Date expirationTime = new Date(now.getTime()+exp);
        if (id==null || id.isEmpty()){
            id= UUID.randomUUID().toString();
        }
        return Jwts.builder() //토큰 발행
                .subject(id)
                .issuedAt(now)
                .expiration(expirationTime)
                .claims(tokenContent)
                .signWith(this.SECRET_KEY)
                .compact();
    }
    public String generateToken(String id, Long exp){
        return generateToken(id, exp, new HashMap<>());
    }

    public String refreshTokenCreate(String id){
        return generateToken(id, REFRESH_VALID_TIME);
    }
    public String accessTokenCreate(String id, Map<String, Object> tokenContent){
        return generateToken(id, VALID_TIME, tokenContent);
    }

    public TokensDTO createToken(String id, Map<String, Object> tokenContent){
        return TokensDTO.builder()
                .accessToken(accessTokenCreate(id, tokenContent))
                .refreshToken(refreshTokenCreate(id))
                .build();
    }
    public TokensDTO createToken(String id, String role){
        Map<String, Object> tokenContent=Map.of("role", role);
        return createToken(id, tokenContent);
    }

    public Claims getToken(String token){
        try {
            // Bearer 접두사 제거
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            // 토큰 서명을 검증하고 내부 데이터(Claims)를 파싱
            // 만료되었거나 누군가 1글자라도 위조했다면 예외(Exception)가 발생.
            return Jwts.parser()
                    //해독기 객체 생성
                    .verifyWith(SECRET_KEY)
                    .build()
                    .parseSignedClaims(token) //서명 및 만료기간 인증. 데이터 복호화
                    .getPayload(); //토큰에서 페이로드만 반환

        } catch (Exception e) {
            // 토큰 만료, 서명 불일치, 올바르지 않은 구조 등 모든 검증 실패 시 null 반환
            return null;
        }
    }
}
