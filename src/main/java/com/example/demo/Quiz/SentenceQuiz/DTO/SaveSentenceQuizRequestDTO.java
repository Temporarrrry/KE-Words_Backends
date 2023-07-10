package com.example.demo.Quiz.SentenceQuiz.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@RequiredArgsConstructor
public class SaveSentenceQuizRequestDTO {
    @NotBlank
    private List<Long> sentenceIds;

    @NotBlank
    private List<String> userAnswers;

    public SentenceQuizRequestDTO toInnerDTO(Long userId) {
        return SentenceQuizRequestDTO.builder()
                .userId(userId)
                .quizDate(LocalDate.now())
                .sentenceIds(sentenceIds)
                .userAnswers(userAnswers)
                .build();
    }
}
