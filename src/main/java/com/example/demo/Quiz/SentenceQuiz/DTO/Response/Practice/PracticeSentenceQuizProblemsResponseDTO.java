package com.example.demo.Quiz.SentenceQuiz.DTO.Response.Practice;

import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Common.CommonSentenceQuizProblemsResponseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class PracticeSentenceQuizProblemsResponseDTO {


    private List<PracticeSentenceQuizProblem> commonProblems = new ArrayList<>();

    public PracticeSentenceQuizProblemsResponseDTO(CommonSentenceQuizProblemsResponseDTO commonSentenceQuizProblemsResponseDTO) {
        this.commonProblems = commonSentenceQuizProblemsResponseDTO
                .getCommonProblems()
                .stream().map(PracticeSentenceQuizProblem::new)
                .toList();
    }
}
