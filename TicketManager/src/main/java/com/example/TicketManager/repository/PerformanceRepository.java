package com.example.TicketManager.repository;

import com.example.TicketManager.dto.PerformanceDTO;
import com.example.TicketManager.model.Performance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerformanceRepository extends JpaRepository<Performance, String> {
    @Query("SELECT new com.example.TicketManager.dto.PerformanceDTO(p.id, p.name, p.startDate, p.endDate, p.location, u.selectedDate) " +
            "FROM UserPerformance u JOIN u.performance p " +
            "WHERE u.user.id = :userId")
    List<PerformanceDTO> findPerformancesByUserId(@Param("userId") Long userId);
    boolean existsById(String id);
}
