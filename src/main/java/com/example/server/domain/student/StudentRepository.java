package com.example.server.domain.student;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {

    boolean existsBySchoolNumber(String schoolNumber);

    Optional<Student> findByUser_LoginId(String userLoginId);

    Optional<Student> findByUser_Id(UUID userId);
}
