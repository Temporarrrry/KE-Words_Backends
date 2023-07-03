package com.example.demo.Quiz.Entity;

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
public class Quiz extends BaseTimeEntity {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private long userId;

    private LocalDate quizDate;

    @Column(nullable = false)
    private String english;

    private int score;

    private int count;

    private String result;


    @Builder
    public Quiz(Long userId, LocalDate quizDate, List<String> english, List<Boolean> result) {
        this.userId = userId;
        this.quizDate = quizDate;
        this.english = String.join("|", english);
        this.score = Long.valueOf(result.stream().filter(Boolean::booleanValue).count()).intValue();
        this.count = result.size();
        this.result = String.join("|", result.stream().map(aBoolean -> (aBoolean) ? "1" : "0").toList());
    }
}