package com.example.server.student;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
    Optional<Student> findByLoginId(String loginId);

    boolean existsByLoginId(String loginId);

    boolean existsBySchoolNumber(String schoolNumber);
}
