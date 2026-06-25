package com.example.server.global;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TeacherToken extends TokenManager {
    public String create(UUID id,String roll){
        Map<String ,Object> content=new HashMap<String,Object>();
        content.put("roll",roll);
        return createToken(id.toString(), content);
    }

}
