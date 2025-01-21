package com.example.TicketManager.repository;

import com.example.TicketManager.dto.PerformanceDTO;
import com.example.TicketManager.model.Performance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PerformanceRepository extends JpaRepository<Performance, String> {
    @Query("SELECT new com.example.TicketManager.dto.PerformanceDTO(p.name, p.startDate, p.endDate, p.location) " +
            "FROM Performance p JOIN p.users u WHERE u.id = :userId")
    List<PerformanceDTO> findPerformancesByUserId(@Param("userId") Long userId);
}
