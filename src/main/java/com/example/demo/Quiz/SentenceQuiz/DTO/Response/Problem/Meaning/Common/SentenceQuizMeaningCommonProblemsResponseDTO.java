package com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Meaning.Common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class SentenceQuizMeaningCommonProblemsResponseDTO {

    private Long quizId;

    private List<SentenceQuizMeaningCommonProblem> meaningProblems = new ArrayList<>();
}
