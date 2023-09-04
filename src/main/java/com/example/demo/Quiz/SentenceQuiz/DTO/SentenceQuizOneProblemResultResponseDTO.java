package com.example.demo.Quiz.SentenceQuiz.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class SentenceQuizOneProblemResultResponseDTO {

    private Long sentenceId;

    private List<String> originalSentence;

    private List<String> problemSentence;

    private List<String> userAnswer;

    private Boolean result;
}
