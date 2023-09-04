package com.example.demo.Quiz.SentenceQuiz.DTO.OrderingQuiz;

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


    private List<String> shuffledEnglish;

    private String korean;

    @Builder
    public OrderingQuizProblem(Long sentenceId, List<String> shuffledEnglish, String korean) {
        this.sentenceId = sentenceId;
        this.shuffledEnglish = shuffledEnglish;
        this.korean = korean;
    }
}
