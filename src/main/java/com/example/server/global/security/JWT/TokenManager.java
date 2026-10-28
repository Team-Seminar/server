package com.example.server.global.security.JWT;

import com.example.server.global.common.dto.TokensDTO;
import com.example.server.global.security.JWT.refreshToken.RefreshToken;
import com.example.server.global.security.JWT.refreshToken.RefreshTokenRepository;
import com.example.server.global.error.exception.CustomException;
import com.example.server.global.error.exception.ErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

//id는 subject에 저장
//role는 역할이나 권한


@Component
public class TokenManager {
    private final SecretKey SECRET_KEY;
    private final RefreshTokenRepository refreshTokenRepository;

    final static private Long VALID_TIME= 30 * 60 * 1000L; //토큰 허용 시간(30분)
    final static private Long REFRESH_VALID_TIME= 14*24*60 * 60 * 1000L; //토큰 허용 시간(2주)

    public void handleTokenException(Exception e) {
        if (e instanceof SecurityException || e instanceof SignatureException) {
            throw new CustomException(ErrorCode.TOKEN_INVALID);
        } else if (e instanceof ExpiredJwtException) {
            throw new CustomException(ErrorCode.TOKEN_EXPIRATION);
        } else if (e instanceof MalformedJwtException) {
            throw new CustomException(ErrorCode.TOKEN_DAMAGED);
        } else if (e instanceof UnsupportedJwtException) {
            throw new CustomException(ErrorCode.TOKEN_BAD_REQUEST);
        } else if (e instanceof IllegalArgumentException) {
            throw new CustomException(ErrorCode.TOKEN_NOT_FOUND);
        }
        throw new CustomException(ErrorCode.TOKEN_FAIL);
    }

    //생성자
    public TokenManager(TokenProperty tokenProperty, RefreshTokenRepository refreshTokenRepository){
        this.refreshTokenRepository=refreshTokenRepository;
        //서명 암호화
        final String SECRET_KEY_STRING = tokenProperty.secretKey(); //보안 키
        if (SECRET_KEY_STRING == null) {
            throw new IllegalStateException("JWT Secret Key가 null입니다. application.yml 설정을 확인하세요.");
        }
        this.SECRET_KEY=Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes(StandardCharsets.UTF_8));
    }


    //해싱
    public String sha256Hashing(String refreshToken){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(refreshToken.getBytes(StandardCharsets.UTF_8));
            return new String(Hex.encode(hash));
        } catch (NoSuchAlgorithmException e) {
            throw new CustomException(ErrorCode.NOT_FOUND_WAY);
        }
    }

    //토큰 생성
    private String generateToken(String id, Long exp , Map<String, Object> tokenContent){
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

    //토큰 생성 : 토큰 내용이 없는 경우
    private String generateToken(String id, Long exp){
        return generateToken(id, exp, new HashMap<>());
    }

    //리프레쉬 토큰 생성
    private String refreshTokenCreate(String id){
        String refreshTokenStr = generateToken(id, REFRESH_VALID_TIME);
        //DB에 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .subject(id)
                .refreshToken(sha256Hashing(refreshTokenStr))
                .build();

        refreshTokenRepository.save(refreshToken);
        return refreshTokenStr;
    }

    //액세스 토큰 생성
    private String accessTokenCreate(String id, Map<String, Object> tokenContent){
        return generateToken(id, VALID_TIME, tokenContent);
    }

    //액세스와 리프레쉬 토큰 생성 및 TokensDTO로 반환
    public TokensDTO createToken(String id, Map<String, Object> tokenContent){
        return TokensDTO.builder()
                .accessToken(accessTokenCreate(id, tokenContent))
                .refreshToken(refreshTokenCreate(id))
                .build();
    }

    //역할 지정 토큰 생성
    public TokensDTO createToken(String id, String role){
        Map<String, Object> tokenContent=Map.of("role", role);
        return createToken(id, tokenContent);
    }

    //토큰의 내용 복호화
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
            handleTokenException(e);
            return null;
        }
    }

    //토큰의 subject내용만 반환
    public String getSubject(String token){
        return getToken(token).getSubject();
    }
}
