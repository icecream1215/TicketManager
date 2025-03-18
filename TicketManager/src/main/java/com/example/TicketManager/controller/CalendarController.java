package com.example.TicketManager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/calendar")
public class CalendarController {
    @GetMapping
    public String showCalendar() {
        return "calendar";
    }

    @GetMapping("/search")
    public String searchPage() {
        return "redirect:/search"; // 공연 검색 페이지로 이동
    }
}
