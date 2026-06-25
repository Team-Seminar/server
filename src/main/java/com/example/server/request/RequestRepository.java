package com.example.server.request;

import com.example.server.classroom.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {
    Optional<Request> findById(Long id);

    List<Request> findAllByClassroom(Classroom classroom);
}
