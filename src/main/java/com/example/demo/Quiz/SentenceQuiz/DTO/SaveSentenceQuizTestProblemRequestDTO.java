package com.example.demo.Quiz.SentenceQuiz.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class SaveSentenceQuizTestProblemRequestDTO {
    private Long sentenceId;

    private List<String> problemSentence;
}

