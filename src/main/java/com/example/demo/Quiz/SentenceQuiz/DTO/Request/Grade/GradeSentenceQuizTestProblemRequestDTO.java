package com.example.demo.Quiz.SentenceQuiz.DTO.Request.Grade;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GradeSentenceQuizTestProblemRequestDTO {
    private Long sentenceId;

    private List<String> userKoreanAnswer;
}
