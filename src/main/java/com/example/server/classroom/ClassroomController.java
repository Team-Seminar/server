package com.example.server.classroom;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
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
}
