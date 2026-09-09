package com.example.server.student;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {

    boolean existsByUser_LoginId(String userLoginId);

    boolean existsBySchoolNumber(String schoolNumber);
}
