package com.example.server.global.security;

import com.example.server.global.TokenManager;
import io.jsonwebtoken.Claims;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@NoArgsConstructor
public class TeacherToken extends TokenManager {
    //토큰 생성
    public String create(UUID id,String roll){
        Map<String ,Object> content=new HashMap<String,Object>();
        content.put("roll",roll);
        return createToken(id.toString(), content);
    }
    //토큰 주인 조회
    public UUID getId(String token){
        Claims claims=getToken(token);
        return UUID.fromString(claims.getId());
    }
    //토큰에서 roll 값 조회
    public String getRoll(String token){
        Claims claims=getToken(token);
        return claims.get("roll",String.class);
    }
}
