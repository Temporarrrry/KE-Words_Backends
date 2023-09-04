package com.example.demo.Quiz.SentenceQuiz.DTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class SaveSentenceQuizRequestDTO {
    private Long sentenceId;

    private List<String> problemSentence;

    private List<String> userAnswer;
}

