package com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Ordering.Common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class SentenceQuizOrderingCommonProblemsResponseDTO {

    private Long quizId;

    private List<SentenceQuizOrderingCommonProblem> problems = new ArrayList<>();
}
