package com.example.server.classroom;
import com.example.server.DTO.ClassroomCreateDTO;
import com.example.server.global.ResponseClass;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/Classrooms")
public class ClassroomController {
    private ResponseClass responseClass;
    final private ClassroomService classroomService;
    @GetMapping("/{id}")
    public Classroom classroomGet(@PathVariable Long id){
        return classroomService.classroomGet(id);
    }
    @GetMapping("/")
    public ResponseEntity<?> classroomGetAll(){
        return responseClass.listReturn(classroomService.classroomGetAll());
    }

    @PostMapping("/")
    public Classroom classroomCreate(@RequestBody ClassroomCreateDTO classroomCreateDTO){
        return classroomService.classroomCreate(classroomCreateDTO.getName());
    }

    @DeleteMapping("/{id}")
    public Classroom classroomDelete(@PathVariable Long id){
        return classroomService.classroomDelete(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestParam ClassroomStatus status){
        classroomService.classroomUpdate(id, status);
        return responseClass.massageReturn("수정이 성공되었습니다");
    }
}
