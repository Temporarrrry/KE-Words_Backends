package com.example.demo.Quiz.WordQuiz.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class SaveWordQuizRequestDTO {
    private List<Long> wordQuizIds;

    private List<List<String>> userKoreanAnswer;

    public WordQuizRequestDTO toInnerDTO(Long userId) {
        return WordQuizRequestDTO.builder()
                .userId(userId)
                .quizDate(LocalDate.now())
                .wordIds(wordQuizIds)
                .build();
    }
}
