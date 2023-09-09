package com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Filling.Test;

import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Filling.Common.SentenceQuizFillingCommonProblemsResponseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class SentenceQuizFillingTestProblemsResponseDTO {

    private Long quizId;

    private List<SentenceQuizFillingTestProblem> problems = new ArrayList<>();

    public SentenceQuizFillingTestProblemsResponseDTO(SentenceQuizFillingCommonProblemsResponseDTO sentenceQuizFillingCommonProblemsResponseDTO) {
        this.quizId = sentenceQuizFillingCommonProblemsResponseDTO.getQuizId();
        this.problems = sentenceQuizFillingCommonProblemsResponseDTO
                .getProblems()
                .stream().map(SentenceQuizFillingTestProblem::new)
                .toList();
    }
}
