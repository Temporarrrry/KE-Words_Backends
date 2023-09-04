package com.example.demo.Quiz.SentenceQuiz.DTO;

import com.example.demo.Quiz.SentenceQuiz.Entity.SentenceQuiz;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class SentenceQuizRequestDTO {

    private Long userId;

    private LocalDate quizDate;

    private List<SaveSentenceQuizRequestDTO> userAnswers;

    private Boolean isTest;

    public SentenceQuiz toEntity(List<Boolean> result) {
        return SentenceQuiz.builder()
                .userId(userId)
                .quizDate(LocalDate.now())
                .sentenceIds(userAnswers.stream().map(SaveSentenceQuizRequestDTO::getSentenceId).toList())
                .problemSentences(userAnswers.stream().map(SaveSentenceQuizRequestDTO::getProblemSentence).toList())
                .userAnswers(userAnswers.stream().map(SaveSentenceQuizRequestDTO::getUserAnswer).toList())
                .result(result)
                .build();
    }
}
