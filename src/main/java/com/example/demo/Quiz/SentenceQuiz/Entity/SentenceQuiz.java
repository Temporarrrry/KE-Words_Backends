package com.example.demo.Quiz.SentenceQuiz.Entity;

import com.example.demo.Common.Entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class SentenceQuiz extends BaseTimeEntity {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    private LocalDate quizDate;

    @Column(nullable = false)
    private String sentenceIds;

    private int score;

    private int count;

    private String result;

    public List<Long> getSentenceIds() {
        return Stream.of(sentenceIds.split("\\|")).map(Long::parseLong).toList();
    }

    @Builder
    public SentenceQuiz(Long userId, LocalDate quizDate, List<Long> sentenceIds, List<Boolean> result) {
        this.userId = userId;
        this.quizDate = quizDate;
        this.sentenceIds = String.join("|", sentenceIds.stream().map(String::valueOf).toList());
        this.score = Long.valueOf(result.stream().filter(Boolean::booleanValue).count()).intValue();
        this.count = result.size();
        this.result = String.join("|", result.stream().map(aBoolean -> (aBoolean) ? "1" : "0").toList());
    }
}