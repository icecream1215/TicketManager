package com.example.TicketManager.controller;

import com.example.TicketManager.dto.PerformanceDTO;
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

import java.time.LocalDate;
import java.util.HashMap;
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
    @ResponseBody
    public ResponseEntity<Map<String, Object>> searchShows(
            @RequestParam("showName") String showName,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            @RequestParam(defaultValue = "1") int page,
            Model model) {

        int rows = 50;
        // 서비스에서 검색 결과 가져오기
        List<Performance> performances = kopisService.getPerformances(showName, startDate, endDate, page, rows);
        boolean hasNextPage = performances.size() >= rows;

        Map<String, Object> result = new HashMap<>();
        result.put("performances", performances);
        result.put("hasNextPage", hasNextPage);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/performances/add")
    @ResponseBody
    public ResponseEntity<String> addPerformanceToUser(@RequestBody PerformanceDTO performanceDTO) {
        try {
            LocalDate selectedDate = performanceDTO.getSelectedDate();
            LocalDate startDate = LocalDate.parse(performanceDTO.getStartDate().replace(".", "-"));
            LocalDate endDate = LocalDate.parse(performanceDTO.getEndDate().replace(".", "-"));

            // 날짜 검증
            if (selectedDate.isBefore(startDate) || selectedDate.isAfter(endDate)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("선택한 날짜가 공연 일정 범위를 벗어났습니다.");
            }

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
            Long userId = userDetails.getUserId();

            performanceService.addPerformanceToUser(userId, performanceDTO);
            return ResponseEntity.ok("공연이 성공적으로 추가되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("공연 추가 중 오류 발생: " + e.getMessage());
        }
    }
}
