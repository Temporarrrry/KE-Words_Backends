package com.example.demo.Quiz.SentenceQuiz.DTO.FillingQuiz;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class FillingQuizResult {

    @NotBlank
    private Long sentenceId;

    @NotBlank
    private List<String> originalEnglish;

    @NotBlank
    private String korean;

    @NotBlank
    private Boolean result;
}
