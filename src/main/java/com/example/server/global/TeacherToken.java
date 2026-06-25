package com.example.server.global;

import io.jsonwebtoken.Claims;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@NoArgsConstructor
public class TeacherToken extends TokenManager {
    public String create(UUID id,String roll){
        Map<String ,Object> content=new HashMap<String,Object>();
        content.put("roll",roll);
        return createToken(id.toString(), content);
    }
    public UUID getId(String token){
        Claims claims=getToken(token);
        return UUID.fromString(claims.getId());
    }

    public String getRoll(String token){
        Claims claims=getToken(token);
        return claims.get("roll",String.class);
    }
}
