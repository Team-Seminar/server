package com.example.server.domain.student;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {

    //학번 기반 유무 조회
    boolean existsBySchoolNumber(String schoolNumber);

    //유저 로그인 아이디 기반 조회
    Optional<Student> findByUser_LoginId(String userLoginId);

    //유저 기본키 기반 조회
    Optional<Student> findByUser_Id(UUID userId);
}
