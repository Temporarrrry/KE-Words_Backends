package com.example.demo.Quiz.WordQuiz.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveWordQuizRequestDTO {

    private List<WordQuizAnswerRequestDTO> userAnswers;

    public WordQuizRequestDTO toInnerDTO(Long userId) {
        return WordQuizRequestDTO.builder()
                .userId(userId)
                .quizDate(LocalDate.now())
                .userAnswers(userAnswers)
                .build();
    }
}
