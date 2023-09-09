package com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Ordering.Practice;

import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Ordering.Common.SentenceQuizOrderingCommonProblemsResponseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class SentenceQuizOrderingPracticeProblemsResponseDTO {


    private List<SentenceQuizOrderingPracticeProblem> problems = new ArrayList<>();

    public SentenceQuizOrderingPracticeProblemsResponseDTO(SentenceQuizOrderingCommonProblemsResponseDTO sentenceQuizOrderingCommonProblemsResponseDTO) {
        this.problems = sentenceQuizOrderingCommonProblemsResponseDTO
                .getProblems()
                .stream().map(SentenceQuizOrderingPracticeProblem::new)
                .toList();
    }
}
