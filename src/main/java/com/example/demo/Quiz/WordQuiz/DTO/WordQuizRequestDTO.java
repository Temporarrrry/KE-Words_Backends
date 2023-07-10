package com.example.demo.Quiz.WordQuiz.DTO;

import com.example.demo.Quiz.WordQuiz.Entity.WordQuiz;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WordQuizRequestDTO {

    @NotBlank
    private Long userId;

    private LocalDate quizDate;

    @NotBlank
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
