package com.example.demo.Quiz.SentenceQuiz.DTO.FillingQuiz;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class FillingQuizProblem {
    private Long sentenceId;

    private List<String> blankedEnglish;

    private String korean;

    @Builder
    public FillingQuizProblem(Long sentenceId, List<String> blankedEnglish, String korean) {
        this.sentenceId = sentenceId;
        this.blankedEnglish = blankedEnglish;
        this.korean = korean;
    }
}
