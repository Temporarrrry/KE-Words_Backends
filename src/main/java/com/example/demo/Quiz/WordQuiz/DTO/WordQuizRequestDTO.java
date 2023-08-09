package com.example.demo.Quiz.WordQuiz.DTO;

import com.example.demo.Quiz.WordQuiz.Entity.WordQuiz;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class WordQuizRequestDTO {

    private Long userId;

    private LocalDate quizDate;

    private List<Long> wordIds;

    private List<List<String>> userKoreanAnswer;

    public WordQuiz toEntity(List<Boolean> result){
        return WordQuiz.builder()
                .userId(userId)
                .quizDate(LocalDate.now())
                .wordIds(wordIds)
                .result(result)
                .build();
    }
}
