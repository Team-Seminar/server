package com.example.server.global;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class ResponseClass {
    Map<String,Object> data=new HashMap<>();
    public void addData(String key, Object value){
        this.data.put(key,value);
    }
    public ResponseEntity<?> responseReturn(){
        Map<String,Object> temp=this.data;
        this.data.clear();
        return ResponseEntity.ok(temp);
    }
    public ResponseEntity<?> oneResponseReturn(String key, Object value){
        return ResponseEntity.ok(new HashMap<String,Object>().put(key, value));
    }
    public ResponseEntity<?> massageReturn(String massage){
        return ResponseEntity.ok(new HashMap<String,String>().put("massage",massage));
    }
    public ResponseEntity<?> tokenReturn(String token){
        return ResponseEntity.ok(new HashMap<String,String>().put("Authorization",token));
    }
}
