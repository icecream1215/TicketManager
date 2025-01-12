package com.example.TicketManager.repository;

import com.example.TicketManager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 사용자 이름으로 검색
    Optional<User> findByUsername(String username);

    // 아이디 중복 확인
    boolean existsByUsername(String username);

    // 이메일 중복 확인
    boolean existsByEmail(String email);
}
