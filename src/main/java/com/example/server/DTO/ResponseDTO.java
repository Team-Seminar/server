package com.example.server.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ResponseDTO {
    public ResponseEntity<Map<String,Object>> stringReturn(String key, Object data){
        Map<String,Object> stringMap=new HashMap<>();
        stringMap.put(key,data);
        return ResponseEntity.ok(stringMap);
    }
}
