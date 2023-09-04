package com.example.demo.Quiz.WordQuiz.DTO;

import com.example.demo.Quiz.WordQuiz.Entity.WordQuiz;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WordQuizRequestDTO {

    private Long userId;

    private LocalDate quizDate;

    private List<SaveWordQuizRequestDTO> userAnswers;

    private Boolean isTest;

    public WordQuiz toEntity(List<Boolean> result){
        return WordQuiz.builder()
                .userId(userId)
                .quizDate(LocalDate.now())
                .wordIds(userAnswers.stream().map(SaveWordQuizRequestDTO::getWordId).toList())
                .koreanChoices(userAnswers.stream().map(SaveWordQuizRequestDTO::getProblemKoreans).toList())
                .userAnswers(userAnswers.stream().map(SaveWordQuizRequestDTO::getUserKoreanAnswer).toList())
                .result(result)
                .build();
    }
}
