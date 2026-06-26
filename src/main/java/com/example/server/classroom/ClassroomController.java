package com.example.server.classroom;

import com.example.server.global.TeacherToken;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/Classrooms")
public class ClassroomController {
    final private ClassroomService classroomService;
    final private TeacherToken teacherToken;
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
    public String updateStatus(@PathVariable Long id,@RequestParam ClassroomStatus status, @RequestHeader("Authorization") String token){
        if(! teacherToken.getRoll(token).equals("teacher")){
            return "권한이 없습니다.";
        }
        classroomService.classroomUpdate(id, status);
        return "수정이 성공되었습니다";
    }
}
