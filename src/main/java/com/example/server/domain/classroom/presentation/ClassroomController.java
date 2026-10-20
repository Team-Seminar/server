package com.example.server.domain.classroom.presentation;
import com.example.server.global.common.dto.ResponseDTO;
import com.example.server.domain.classroom.Classroom;
import com.example.server.domain.classroom.ClassroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/classrooms")
public class ClassroomController {
    final private ClassroomService classroomService;
    @GetMapping("/{id}")
    public ResponseDTO<Classroom> classroomGet(@PathVariable Long id){
        return ResponseDTO.<Classroom>success(classroomService.classroomGet(id));
    }
    @GetMapping()
    public ResponseDTO<List<Classroom>> classroomGetAll(){
        return ResponseDTO.<List<Classroom>>success(classroomService.classroomGetAll());
    }

}
