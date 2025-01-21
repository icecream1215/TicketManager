package com.example.TicketManager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor  // 생성자 추가
public class PerformanceDTO {
    private String name;
    private String startDate;
    private String endDate;
    private String location;
}