package com.example.demo.Quiz.WordQuiz.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WordQuizAnswerRequestDTO {
    private Long wordId;

    private List<String> userKoreanAnswer;
}
