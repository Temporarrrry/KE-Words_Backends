package com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Filling.Practice;

import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Filling.Common.SentenceQuizFillingCommonProblemsResponseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class SentenceQuizFillingPracticeProblemsResponseDTO {


    private List<SentenceQuizFillingPracticeProblem> problems = new ArrayList<>();

    public SentenceQuizFillingPracticeProblemsResponseDTO(SentenceQuizFillingCommonProblemsResponseDTO sentenceQuizFillingCommonProblemsResponseDTO) {
        this.problems = sentenceQuizFillingCommonProblemsResponseDTO
                .getProblems()
                .stream().map(SentenceQuizFillingPracticeProblem::new)
                .toList();
    }
}
