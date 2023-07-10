package com.example.demo.Quiz.WordQuiz.Entity;

import com.example.demo.Common.Entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class WordQuiz extends BaseTimeEntity {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    private LocalDate quizDate;

    @Column(nullable = false)
    private String wordIds;

    private int score;

    private int count;

    private String result;


    @Builder
    public WordQuiz(Long userId, LocalDate quizDate, List<Long> wordIds, List<Boolean> result) {
        this.userId = userId;
        this.quizDate = quizDate;
        this.wordIds = String.join("|", wordIds.stream().map(String::valueOf).toList());
        this.score = Long.valueOf(result.stream().filter(Boolean::booleanValue).count()).intValue();
        this.count = result.size();
        this.result = String.join("|", result.stream().map(aBoolean -> (aBoolean) ? "1" : "0").toList());
    }
}