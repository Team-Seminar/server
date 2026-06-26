package com.example.server.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ResponseDTO {
    Map<String,Object> data=new HashMap<>();
    public void addData(String key, Object value){
        this.data.put(key,value);
    }
    public ResponseEntity<Map<String,Object>> responseReturn(){
        return ResponseEntity.ok(this.data);
    }
}
