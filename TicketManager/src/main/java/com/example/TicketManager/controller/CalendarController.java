package com.example.TicketManager.controller;

import com.example.TicketManager.model.Performance;
import com.example.TicketManager.service.KopisService;
import com.example.TicketManager.service.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CalendarController {
    @Autowired
    private PerformanceService performanceService;
    @Autowired
    private KopisService kopisService;

    @GetMapping("/calendar")
    public String showCalendar(Model model) {
        List<Performance> performances = performanceService.getAllPerformances();
        model.addAttribute("performances", performances);
        return "calendar";  // calendar.html 뷰를 반환
    }

    @GetMapping("/test")
    public String testApi(Model model) {
        // KOPIS API 또는 다른 데이터 소스를 통해 공연 정보를 가져옵니다.
        List<Performance> performances = kopisService.getPerformances();

        // 가져온 데이터를 콘솔에 출력하여 확인
        performances.forEach(performance -> {
            System.out.println("Performance Title: " + performance.getName());
            System.out.println("Performance Location: " + performance.getLocation());
            System.out.println("Performance Date: " + performance.getStartDate());
        });

        // 데이터를 화면에 표시하기 위해 모델에 추가
        model.addAttribute("performances", performances);

        // test.html로 데이터를 전달하여 확인
        return "test";
    }
}
