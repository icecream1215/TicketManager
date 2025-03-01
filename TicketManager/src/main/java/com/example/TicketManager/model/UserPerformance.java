package com.example.TicketManager.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name="user_performance")
@Getter
@Setter
@NoArgsConstructor
public class UserPerformance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "performance_id", nullable = false)
    private Performance performance;

    @Column(nullable = false)
    private LocalDate selectedDate; // 사용자가 선택한 날짜

    // 생성자 추가
    public UserPerformance(User user, Performance performance, LocalDate selectedDate) {
        this.user = user;
        this.performance = performance;
        this.selectedDate = selectedDate;
    }
}
