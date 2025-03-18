package com.example.TicketManager.repository;

import com.example.TicketManager.model.UserPerformance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Repository
public interface UserPerformanceRepository extends JpaRepository<UserPerformance, String> {
    boolean existsByUserIdAndPerformanceIdAndSelectedDate(Long userId, String performanceId, LocalDate selectedDate);

    @Transactional
    void deleteByUserIdAndPerformanceIdAndSelectedDate(Long userId, String performanceId, LocalDate selectedDate);

}
