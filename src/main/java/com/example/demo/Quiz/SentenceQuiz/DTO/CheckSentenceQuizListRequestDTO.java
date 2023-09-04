package com.example.demo.Quiz.SentenceQuiz.DTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@RequiredArgsConstructor
public class CheckSentenceQuizListRequestDTO {
    private List<SaveSentenceQuizRequestDTO> userAnswers;

    private Boolean isTest;


    public SentenceQuizRequestDTO toInnerDTO(Long userId) {
        return SentenceQuizRequestDTO.builder()
                .userId(userId)
                .quizDate(LocalDate.now())
                .userAnswers(userAnswers)
                .isTest(isTest)
                .build();
    }
}
