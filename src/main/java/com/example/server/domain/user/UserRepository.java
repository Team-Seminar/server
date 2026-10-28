package com.example.server.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, String> {

    //로그인 아이디 기반 조회
    Optional<User> findByLoginId(String loginId);

    //로그인 아이디 유무 조회
    boolean existsByLoginId(String loginId);
}
