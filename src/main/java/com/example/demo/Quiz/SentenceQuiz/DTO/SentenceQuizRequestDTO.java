package com.example.demo.Quiz.SentenceQuiz.DTO;

import com.example.demo.Quiz.SentenceQuiz.Entity.SentenceQuiz;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@RequiredArgsConstructor
public class SentenceQuizRequestDTO {

    private Long userId;

    private LocalDate quizDate;

    @NotBlank
    private List<Long> sentenceIds;

    @NotBlank
    private List<String> userAnswers;

    public SentenceQuiz toEntity(List<Boolean> result) {
        return SentenceQuiz.builder()
                .userId(userId)
                .quizDate(LocalDate.now())
                .sentenceIds(sentenceIds)
                .result(result)
                .build();
    }
}
