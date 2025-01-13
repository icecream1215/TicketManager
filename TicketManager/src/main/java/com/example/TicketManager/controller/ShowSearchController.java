package com.example.TicketManager.controller;

import com.example.TicketManager.model.Performance;
import com.example.TicketManager.service.CustomUserDetails;
import com.example.TicketManager.service.KopisService;
import com.example.TicketManager.service.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/search")
public class ShowSearchController {
    @Autowired
    private KopisService kopisService;
    @Autowired
    private PerformanceService performanceService;

    @GetMapping
    public String showSearchPage() {
        return "search"; // 검색 페이지 템플릿
    }

    @PostMapping
    public String searchShows(
            @RequestParam("showName") String showName,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            Model model) {
        System.out.println("showName: " + showName);
        System.out.println("startDate: " + startDate);
        System.out.println("endDate: " + endDate);

        // 서비스에서 검색 결과 가져오기
        List<Performance> performances = kopisService.getPerformances(showName, startDate, endDate);

        // 검색 결과를 모델에 추가
        model.addAttribute("performances", performances);
        return "search";
    }

    @PostMapping("/performances/add")
    @ResponseBody
    public ResponseEntity<String> addPerformanceToUser(@RequestBody Performance performance) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
            Long userId = userDetails.getUserId();

            performanceService.addPerformanceToUser(userId, performance);
            return ResponseEntity.ok("공연이 성공적으로 추가되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("공연 추가 중 오류 발생: " + e.getMessage());
        }
    }

}
