package com.example.TicketManager.repository;

import com.example.TicketManager.model.Performance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformanceRepository extends JpaRepository<Performance, String> {
}
