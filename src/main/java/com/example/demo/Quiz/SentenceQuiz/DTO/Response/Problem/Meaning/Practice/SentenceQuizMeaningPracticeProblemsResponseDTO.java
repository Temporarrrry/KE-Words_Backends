package com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Meaning.Practice;

import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Meaning.Common.SentenceQuizMeaningCommonProblemsResponseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class SentenceQuizMeaningPracticeProblemsResponseDTO {

    private List<SentenceQuizMeaningPracticeProblem> meaningProblems = new ArrayList<>();

    public SentenceQuizMeaningPracticeProblemsResponseDTO(SentenceQuizMeaningCommonProblemsResponseDTO sentenceQuizMeaningCommonProblemsResponseDTO) {
        this.meaningProblems = sentenceQuizMeaningCommonProblemsResponseDTO
                .getMeaningProblems()
                .stream().map(SentenceQuizMeaningPracticeProblem::new)
                .toList();
    }
}
