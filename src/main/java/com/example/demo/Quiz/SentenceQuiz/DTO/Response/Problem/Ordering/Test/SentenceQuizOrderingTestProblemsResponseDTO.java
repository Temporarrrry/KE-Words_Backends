package com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Ordering.Test;

import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Ordering.Common.SentenceQuizOrderingCommonProblemsResponseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class SentenceQuizOrderingTestProblemsResponseDTO {

    private Long quizId;

    private List<SentenceQuizOrderingTestProblem> problems = new ArrayList<>();

    public SentenceQuizOrderingTestProblemsResponseDTO(SentenceQuizOrderingCommonProblemsResponseDTO sentenceQuizOrderingCommonProblemsResponseDTO) {
        this.quizId = sentenceQuizOrderingCommonProblemsResponseDTO.getQuizId();
        this.problems = sentenceQuizOrderingCommonProblemsResponseDTO
                .getProblems()
                .stream().map(SentenceQuizOrderingTestProblem::new)
                .toList();
    }
}
