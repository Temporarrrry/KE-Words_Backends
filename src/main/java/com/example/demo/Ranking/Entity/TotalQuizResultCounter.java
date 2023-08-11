package com.example.demo.Ranking.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@IdClass(TotalQuizResultPk.class)
public class TotalQuizResultCounter {

    @Id
    @Column(nullable = false)
    private Long userId;

    @Id
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TotalQuizResultType type;

    @Column(nullable = false)
    private Long correctCount;

    @Column(nullable = false)
    private Long totalCount;

    public TotalQuizResultCounter(Long userId, TotalQuizResultType type, Long correctCount, Long totalCount) {
        this.userId = userId;
        this.type = type;
        this.correctCount = correctCount;
        this.totalCount = totalCount;
    }
}