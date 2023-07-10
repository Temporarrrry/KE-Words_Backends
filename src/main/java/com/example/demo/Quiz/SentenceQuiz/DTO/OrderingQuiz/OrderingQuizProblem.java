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
    @NotBlank
    private Long sentenceId;

    @NotBlank
    private List<String> originalEnglish;

    @NotBlank
    private List<String> shuffledEnglish;

    @NotBlank
    private String korean;

    @Builder
    public OrderingQuizProblem(Long sentenceId, List<String> originalEnglish, List<String> shuffledEnglish, String korean) {
        this.sentenceId = sentenceId;
        this.originalEnglish = originalEnglish;
        this.shuffledEnglish = shuffledEnglish;
        this.korean = korean;
    }
}
