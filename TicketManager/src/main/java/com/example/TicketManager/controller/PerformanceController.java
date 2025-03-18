package com.example.TicketManager.controller;

import com.example.TicketManager.dto.PerformanceDTO;
import com.example.TicketManager.service.CustomUserDetails;
import com.example.TicketManager.service.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController

@RequestMapping("/performances")

public class PerformanceController {
    @Autowired
    private PerformanceService performanceService;

    @GetMapping("/user")
    @ResponseBody
    public List<Map<String, Object>> getUserPerformances(@AuthenticationPrincipal CustomUserDetails user) {
        List<PerformanceDTO> performances = performanceService.getPerformancesByUser(user.getUserId());

        return performances.stream().map(performance -> {
            Map<String, Object> event = new HashMap<>();
            event.put("id", performance.getId());
            event.put("title", performance.getName());
            event.put("start", performance.getSelectedDate()); // 내가 선택한 날짜
            event.put("startDate", performance.getStartDate()); // 공연 기간 (시작)
            event.put("endDate", performance.getEndDate()); // 공연 기간 (종료)
            event.put("location", performance.getLocation()); // 공연 장소 추가
            return event;
        }).collect(Collectors.toList());
    }

    @DeleteMapping("/user/{performanceId}/{selectedDate}")
    @ResponseBody
    public ResponseEntity<?> deleteSelectedPerformanceDate(@PathVariable String performanceId,
                                                           @PathVariable LocalDate selectedDate,
                                                           @AuthenticationPrincipal CustomUserDetails user) {
        performanceService.deletePerformanceByIdAndSelectedDate(user.getUserId(), performanceId, selectedDate);
        return ResponseEntity.ok("선택한 날짜의 공연이 삭제되었습니다!");
    }
}
