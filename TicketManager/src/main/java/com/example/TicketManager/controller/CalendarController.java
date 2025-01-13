package com.example.TicketManager.controller;

import com.example.TicketManager.model.Performance;
import com.example.TicketManager.service.KopisService;
import com.example.TicketManager.service.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String showCalendar(Model model) {
        //사용자가 저장한 공연 정보 출력

        //List<Performance> performances = performanceService.getAllPerformances();
        //model.addAttribute("performances", performances);
        return "calendar";  // calendar.html 뷰를 반환
    }

    @GetMapping("/search")
    public String searchPage() {
        return "redirect:/search"; // 공연 검색 페이지로 이동
    }
}
