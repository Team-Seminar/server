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

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO classroomCreate(@RequestBody ClassroomCreateDTO classroomCreateDTO){
        return ResponseDTO.success(classroomService.classroomCreate(classroomCreateDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void classroomDelete(@PathVariable Long id){
        classroomService.classroomDelete(id);
    }

    @PatchMapping("/{id}")
    public ResponseDTO updateStatus(@PathVariable Long id, @RequestParam ClassroomStatus status){
        classroomService.classroomUpdate(id, status);
        return ResponseDTO.success("수정 성공");
    }
}
