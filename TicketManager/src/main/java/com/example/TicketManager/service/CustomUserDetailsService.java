package com.example.TicketManager.service;

import com.example.TicketManager.model.User;
import com.example.TicketManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 사용자 조회
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));

        // 사용자의 정보를 UserDetails로 반환 (기본적으로 UsernamePasswordAuthenticationToken에 사용됨)
//        return org.springframework.security.core.userdetails.User
//                .withUsername(user.getUsername())
//                .password(user.getPassword())  // 암호화된 비밀번호
//                .roles("USER")  // 필요한 권한을 설정
//                .build();
        return new CustomUserDetails(user.getId(), user.getUsername(), user.getPassword(), user.getAuthorities());

    }
}
