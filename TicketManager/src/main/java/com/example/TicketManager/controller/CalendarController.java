package com.example.TicketManager.controller;

import com.example.TicketManager.dto.PerformanceDTO;
import com.example.TicketManager.service.CustomUserDetails;
import com.example.TicketManager.service.KopisService;
import com.example.TicketManager.service.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/calendar")
public class CalendarController {
    @Autowired
    private PerformanceService performanceService;
    @Autowired
    private KopisService kopisService;

    @GetMapping
    public String showCalendar(Model model, @AuthenticationPrincipal CustomUserDetails user) {
        List<PerformanceDTO> performances = performanceService.getPerformancesByUser(user.getUserId());
        model.addAttribute("performances", performances);
        return "calendar";
    }

    @GetMapping("/search")
    public String searchPage() {
        return "redirect:/search"; // 공연 검색 페이지로 이동
    }
}
