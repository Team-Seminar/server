package com.example.server.classroom;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
    void removeById(Long classId);
}
