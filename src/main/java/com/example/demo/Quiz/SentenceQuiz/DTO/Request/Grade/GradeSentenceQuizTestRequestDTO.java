package com.example.demo.Quiz.SentenceQuiz.DTO.Request.Grade;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GradeSentenceQuizTestRequestDTO {

    private List<GradeSentenceQuizTestProblemRequestDTO> userAnswers;
}
