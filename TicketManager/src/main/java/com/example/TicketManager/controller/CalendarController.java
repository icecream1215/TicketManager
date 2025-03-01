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
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/calendar")
public class CalendarController {
    @Autowired
    private PerformanceService performanceService;
    @Autowired
    private KopisService kopisService;

    @GetMapping
    public String showCalendar() {
        return "calendar";
    }

    @GetMapping("/user/performances")
    @ResponseBody
    public List<Map<String, Object>> getUserPerformances(@AuthenticationPrincipal CustomUserDetails user) {
        List<PerformanceDTO> performances = performanceService.getPerformancesByUser(user.getUserId());

        return performances.stream().map(performance -> {
            Map<String, Object> event = new HashMap<>();
            event.put("title", performance.getName());
            event.put("start", performance.getSelectedDate()); // 선택한 날짜
            return event;
        }).collect(Collectors.toList());
    }

    @GetMapping("/search")
    public String searchPage() {
        return "redirect:/search"; // 공연 검색 페이지로 이동
    }
}
