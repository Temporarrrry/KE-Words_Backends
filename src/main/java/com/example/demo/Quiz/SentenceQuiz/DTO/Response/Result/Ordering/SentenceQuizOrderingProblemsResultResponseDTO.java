package com.example.demo.Quiz.SentenceQuiz.DTO.Response.Result.Ordering;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class SentenceQuizOrderingProblemsResultResponseDTO {

    private Long quizId;

    private Long userId;

    private Integer correctCount;

    private Integer totalCount;

    private List<SentenceQuizOrderingProblemResult> problemResults = new ArrayList<>();
}
