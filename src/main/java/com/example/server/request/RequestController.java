package com.example.server.request;

import com.example.server.classroom.Classroom;
import com.example.server.classroom.ClassroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Request")
public class RequestController {
    final private RequestService requestService;
    final private ClassroomService classroomService;

    @GetMapping("/Create")
    public void RequestCreate(@RequestParam Long tableId, @RequestParam String reason, @RequestParam int time){
        Classroom classroom =classroomService.classroomGet(tableId);
        requestService.requestCreate(time, reason, classroom);
    }
    @GetMapping("/Get")
    public Request RequestGet(@RequestParam Long id){
        return requestService.requestGet(id);
    }

    @GetMapping("/GetAll")
    public List<Request> RequestGetAll(){
        return requestService.requestGetAll();
    }
}
