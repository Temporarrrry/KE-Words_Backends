package com.example.demo.Quiz.SentenceQuiz.DTO.Response.Common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class CommonSentenceQuizProblemsResponseDTO {

    private Long quizId;

    private List<CommonSentenceQuizProblem> commonProblems = new ArrayList<>();
}
