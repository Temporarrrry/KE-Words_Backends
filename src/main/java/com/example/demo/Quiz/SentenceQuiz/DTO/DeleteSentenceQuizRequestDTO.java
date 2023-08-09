package com.example.demo.Quiz.SentenceQuiz.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class DeleteSentenceQuizRequestDTO {
    private Long sentenceQuizId;
}
