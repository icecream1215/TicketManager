package com.example.TicketManager.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserDetails extends User {
    private Long userId;

    // User 객체의 생성자를 호출하고, userId를 추가로 저장
    public CustomUserDetails(Long userId, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userId = userId;
    }

    // 실제 userId (PK)를 반환하는 메서드
    public Long getUserId() {
        return userId;
    }
}