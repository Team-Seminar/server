package com.example.server.global;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
public class TokenManager {
    private static final String SECRET_KEY_STRING="testsecretkey"; //보안 키
    private static final SecretKey SECRET_KEY= Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes(StandardCharsets.UTF_8)); //암호화
    private static final Long VALID_TIME= 30 * 60 * 1000L; //토큰 허용 시간(30분)
    public String createToken(String id, Map<String, Objects> tokenContent){
        Date now=new Date();
        Date expirationTime=new Date(now.getTime()+VALID_TIME);
        if (id==null || id.isEmpty()){
            id= UUID.randomUUID().toString();
        }
        return Jwts.builder()
                .subject(id)
                .issuedAt(now)
                .expiration(expirationTime)
                .claims(tokenContent)
                .signWith(SECRET_KEY)
                .compact();

    }
}
