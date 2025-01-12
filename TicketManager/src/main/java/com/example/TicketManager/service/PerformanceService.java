package com.example.TicketManager.service;

import com.example.TicketManager.model.Performance;
import com.example.TicketManager.repository.PerformanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerformanceService  {
    private final KopisService kopisService;
    private final PerformanceRepository performanceRepository;

    @Autowired
    public PerformanceService(KopisService kopisService, PerformanceRepository performanceRepository) {
        this.kopisService = kopisService;
        this.performanceRepository = performanceRepository;
    }

    // 공연 정보를 DB에 저장
    public void savePerformances() {
        List<Performance> performances = kopisService.getPerformances();
        performanceRepository.saveAll(performances);
    }

    // 저장된 공연 목록 가져오기
    public List<Performance> getAllPerformances() {
        return performanceRepository.findAll();
    }
}
