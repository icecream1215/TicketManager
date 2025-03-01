package com.example.TicketManager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor  // 생성자 추가
public class PerformanceDTO {
    private String id;
    private String name;
    private String startDate;
    private String endDate;
    private String location;
    private LocalDate selectedDate;
}