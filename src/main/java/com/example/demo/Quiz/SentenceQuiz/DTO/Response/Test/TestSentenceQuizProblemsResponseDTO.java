package com.example.demo.Quiz.SentenceQuiz.DTO.Response.Test;

import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Common.CommonSentenceQuizProblemsResponseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class TestSentenceQuizProblemsResponseDTO {

    private Long quizId;

    private List<TestSentenceQuizProblem> commonProblems = new ArrayList<>();

    public TestSentenceQuizProblemsResponseDTO(CommonSentenceQuizProblemsResponseDTO commonSentenceQuizProblemsResponseDTO) {
        this.quizId = commonSentenceQuizProblemsResponseDTO.getQuizId();
        this.commonProblems = commonSentenceQuizProblemsResponseDTO
                .getCommonProblems()
                .stream().map(TestSentenceQuizProblem::new)
                .toList();
    }
}
