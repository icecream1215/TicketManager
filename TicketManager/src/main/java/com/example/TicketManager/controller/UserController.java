package com.example.TicketManager.controller;

import com.example.TicketManager.model.User;
import com.example.TicketManager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class UserController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    // 로그인 페이지
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // 회원가입 폼
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // 회원가입 처리
    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model) {
        // 아이디 중복 확인
        if (userService.isUsernameTaken(user.getUsername())) {
            model.addAttribute("usernameError", "아이디가 이미 존재합니다.");
        }

        // 이메일 중복 확인
        if (userService.isEmailTaken(user.getEmail())) {
            model.addAttribute("emailError", "이메일이 이미 존재합니다.");
        }

        // 중복이 없다면 회원가입 진행
        if (!model.containsAttribute("usernameError") && !model.containsAttribute("emailError")) {
            // 비밀번호 암호화 후 저장
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.save(user);
            return "redirect:/login"; // 회원가입 성공 후 로그인 페이지로 리디렉션
        }

        return "register"; // 에러가 있으면 회원가입 페이지로 돌아감
    }
}
