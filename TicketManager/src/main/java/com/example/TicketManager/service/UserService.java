package com.example.TicketManager.service;

import com.example.TicketManager.model.User;
import com.example.TicketManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    // 아이디 중복 확인
    public boolean isUsernameTaken(String username) {
        return userRepository.existsByUsername(username);
    }

    // 이메일 중복 확인
    public boolean isEmailTaken(String email) {
        return userRepository.existsByEmail(email);
    }

    // 사용자 저장
    public void save(User user) {
        userRepository.save(user);
    }
}
