package com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Meaning.Test;

import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Meaning.Common.SentenceQuizMeaningCommonProblemsResponseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class SentenceQuizMeaningTestProblemsResponseDTO {

    private Long quizId;

    private List<SentenceQuizMeaningTestProblem> meaningProblems = new ArrayList<>();

    public SentenceQuizMeaningTestProblemsResponseDTO(SentenceQuizMeaningCommonProblemsResponseDTO sentenceQuizMeaningCommonProblemsResponseDTO) {
        this.quizId = sentenceQuizMeaningCommonProblemsResponseDTO.getQuizId();
        this.meaningProblems = sentenceQuizMeaningCommonProblemsResponseDTO
                .getMeaningProblems()
                .stream().map(SentenceQuizMeaningTestProblem::new)
                .toList();
    }
}
