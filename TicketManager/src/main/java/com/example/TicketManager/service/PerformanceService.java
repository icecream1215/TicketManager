package com.example.TicketManager.service;

import com.example.TicketManager.dto.PerformanceDTO;
import com.example.TicketManager.model.Performance;
import com.example.TicketManager.model.User;
import com.example.TicketManager.model.UserPerformance;
import com.example.TicketManager.repository.PerformanceRepository;
import com.example.TicketManager.repository.UserPerformanceRepository;
import com.example.TicketManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PerformanceService {
    private final PerformanceRepository performanceRepository;
    private final UserRepository userRepository; // 사용자 저장소
    private final UserPerformanceRepository userPerformanceRepository;


    @Autowired
    public PerformanceService(PerformanceRepository performanceRepository,
                              UserRepository userRepository,
                              UserPerformanceRepository userPerformanceRepository) {
        this.performanceRepository = performanceRepository;
        this.userRepository = userRepository;
        this.userPerformanceRepository = userPerformanceRepository;
    }

    // 공연 정보를 DB에 저장
    public void savePerformances(List<Performance> performances) {
        for (Performance performance : performances) {
            if (!performanceRepository.existsById(performance.getId())) {
                performanceRepository.save(performance);
            }
        }
    }

    // 선택한 공연 정보 저장
    public void addPerformanceToUser(Long userId, PerformanceDTO performanceDTO) {
        // 사용자 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // 공연이 이미 Performance 테이블에 있는지 확인
        Performance existingPerformance = performanceRepository.findById(performanceDTO.getId()).orElse(null);
        if (existingPerformance == null) {
            // 공연 정보가 없으면 저장
            existingPerformance = new Performance(
                    performanceDTO.getId(),
                    performanceDTO.getName(),
                    performanceDTO.getStartDate(),
                    performanceDTO.getEndDate(),
                    performanceDTO.getLocation()
            );
            existingPerformance = performanceRepository.save(existingPerformance);
        }

        // 사용자가 이미 해당 공연을 추가했는지 확인(`user_performance` 테이블에 저장)
        LocalDate selectedDate = performanceDTO.getSelectedDate();
        boolean alreadyExists = userPerformanceRepository.existsByUserIdAndPerformanceIdAndSelectedDate(
                userId, existingPerformance.getId(), selectedDate
        );

        if (!alreadyExists) {
            UserPerformance userPerformance = new UserPerformance(user, existingPerformance, selectedDate);
            userPerformanceRepository.save(userPerformance);
        }
    }

    public List<PerformanceDTO> getPerformancesByUser(Long userId) {
        List<PerformanceDTO> performances = performanceRepository.findPerformancesByUserId(userId);
        return performances.stream()
                .map(performanceDTO -> {
                    // 날짜 변환 로직 추가
                    String formattedStartDate = formatDate(performanceDTO.getStartDate());
                    String formattedEndDate = formatDate(performanceDTO.getEndDate());

                    // 변환된 날짜로 DTO 업데이트
                    performanceDTO.setStartDate(formattedStartDate);
                    performanceDTO.setEndDate(formattedEndDate);

                    return performanceDTO; // 변환된 DTO 반환
                })
                .collect(Collectors.toList());
    }

    private String formatDate(String dateString) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy.MM.dd");  // 기존 형식
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");  // 원하는 형식
            Date date = inputFormat.parse(dateString);  // 문자열을 Date로 변환
            return outputFormat.format(date);  // 원하는 형식으로 반환
        } catch (ParseException e) {
            e.printStackTrace();
            return dateString; // 변환 실패 시 원본 반환
        }
    }
}
