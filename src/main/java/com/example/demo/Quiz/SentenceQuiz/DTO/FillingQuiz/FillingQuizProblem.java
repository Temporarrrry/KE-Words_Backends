package com.example.demo.Quiz.SentenceQuiz.DTO.FillingQuiz;

import jakarta.validation.constraints.NotBlank;
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

    private List<String> originalEnglish;

    private List<String> blankedEnglish;

    private List<String> korean;

    @Builder
    public FillingQuizProblem(Long sentenceId, List<String> originalEnglish, List<String> blankedEnglish, List<String> korean) {
        this.sentenceId = sentenceId;
        this.originalEnglish = originalEnglish;
        this.blankedEnglish = blankedEnglish;
        this.korean = korean;
    }
}
