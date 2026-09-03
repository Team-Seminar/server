package com.example.server.classroom;
import com.example.server.DTO.ClassroomCreateDTO;
import com.example.server.DTO.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/classrooms")
public class ClassroomController {
    final private ClassroomService classroomService;
    @GetMapping("/{id}")
    public ResponseDTO classroomGet(@PathVariable Long id){
        return ResponseDTO.success(classroomService.classroomGet(id));
    }
    @GetMapping()
    public ResponseDTO classroomGetAll(){
        return ResponseDTO.success(classroomService.classroomGetAll());
    }

}
