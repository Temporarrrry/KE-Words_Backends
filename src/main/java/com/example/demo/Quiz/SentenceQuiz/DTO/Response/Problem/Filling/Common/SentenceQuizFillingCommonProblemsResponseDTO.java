package com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Filling.Common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class SentenceQuizFillingCommonProblemsResponseDTO {

    private Long quizId;

    private List<SentenceQuizFillingCommonProblem> problems = new ArrayList<>();
}
