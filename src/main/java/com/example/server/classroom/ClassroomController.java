package com.example.server.classroom;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Classroom")
public class ClassroomController {
    final private ClassroomService classroomService;
    @GetMapping("/Get")
    public Classroom classroomGet(Long id){
        return classroomService.classroomGet(id);
    }
    @GetMapping("/GetAll")
    public List<Classroom> classroomGetAll(){
        return classroomService.classroomGetAll();
    }

    @GetMapping("/Create")
    public Classroom classroomCreate(@RequestParam String name){
        return classroomService.classroomCreate(name);
    }

    @GetMapping("/Delete")
    public Classroom classroomDelete(@RequestParam Long id){
        return classroomService.classroomDelete(id);
    }

    @GetMapping("/UpdateName")
    public String UpdateName(@RequestParam Long id, @RequestParam String name){
        classroomService.classroomUpdate(id, name);
        return "수정이 성공되었습니다";
    }

    @GetMapping("/UpdateStatus")
    public String updateStatus(@RequestParam Long id,@RequestParam ClassroomStatus status){
        classroomService.classroomUpdate(id, status);
        return "수정이 성공되었습니다";
    }
}
