package com.example.demo.Quiz.SentenceQuiz.DTO.OrderingQuiz;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class OrderingQuizResult {

    private Long sentenceId;

    private List<String> originalEnglish;

    @NotBlank
    private String korean;

    private Boolean result;
}
