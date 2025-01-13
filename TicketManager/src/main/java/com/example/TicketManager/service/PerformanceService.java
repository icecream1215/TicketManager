package com.example.TicketManager.service;

import com.example.TicketManager.model.Performance;
import com.example.TicketManager.model.User;
import com.example.TicketManager.repository.PerformanceRepository;
import com.example.TicketManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerformanceService {
    private final KopisService kopisService;
    private final PerformanceRepository performanceRepository;
    private final UserRepository userRepository; // 사용자 저장소


    @Autowired
    public PerformanceService(KopisService kopisService, PerformanceRepository performanceRepository, UserRepository userRepository) {
        this.kopisService = kopisService;
        this.performanceRepository = performanceRepository;
        this.userRepository = userRepository;
    }

    // 공연 정보를 DB에 저장
    public void savePerformances() {
    }

    // 저장된 공연 목록 가져오기
    public List<Performance> getAllPerformances() {
        return performanceRepository.findAll();
    }

    public void addPerformanceToUser(Long userId, Performance performance) {
        // 사용자 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // 공연이 이미 Performance 테이블에 있는지 확인
        Performance existingPerformance = performanceRepository.findById(performance.getId()).orElse(null);
        if (existingPerformance == null) {
            // 공연 정보가 없으면 저장
            existingPerformance = performanceRepository.save(performance);
        }

        // 사용자-공연 관계 추가
        if (!user.getPerformances().contains(existingPerformance)) {
            user.getPerformances().add(existingPerformance);
            userRepository.save(user);
        }
    }
}
