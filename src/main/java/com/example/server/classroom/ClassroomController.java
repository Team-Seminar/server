package com.example.server.classroom;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/Classrooms")
public class ClassroomController {
    final private ClassroomService classroomService;
    @GetMapping("/{id}")
    public Classroom classroomGet(@PathVariable Long id){
        return classroomService.classroomGet(id);
    }
    @GetMapping()
    public List<Classroom> classroomGetAll(){
        return classroomService.classroomGetAll();
    }

    @PostMapping()
    public Classroom classroomCreate(@RequestParam String name){
        return classroomService.classroomCreate(name);
    }

    @DeleteMapping("/{id}")
    public Classroom classroomDelete(@PathVariable Long id){
        return classroomService.classroomDelete(id);
    }

    @PatchMapping("/{id}")
    public String updateStatus(@PathVariable Long id,@RequestParam ClassroomStatus status){
        classroomService.classroomUpdate(id, status);
        return "수정이 성공되었습니다";
    }
}
