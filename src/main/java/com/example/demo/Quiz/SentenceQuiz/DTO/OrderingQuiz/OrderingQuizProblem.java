package com.example.demo.Quiz.SentenceQuiz.DTO.OrderingQuiz;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor

public class OrderingQuizProblem {
    private Long sentenceId;

    private List<String> originalEnglish;

    private List<String> shuffledEnglish;

    private List<String> korean;

    @Builder
    public OrderingQuizProblem(Long sentenceId, List<String> originalEnglish, List<String> shuffledEnglish, List<String> korean) {
        this.sentenceId = sentenceId;
        this.originalEnglish = originalEnglish;
        this.shuffledEnglish = shuffledEnglish;
        this.korean = korean;
    }
}
