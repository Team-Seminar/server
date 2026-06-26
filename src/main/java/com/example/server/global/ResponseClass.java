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
    public ResponseEntity<Map<String,Object>> responseReturn(){
        return ResponseEntity.ok(this.data);
    }
    public ResponseEntity<?> massageReturn(String massage){
        return ResponseEntity.ok(new HashMap<String,String>().put("massage",massage));
    }
}
