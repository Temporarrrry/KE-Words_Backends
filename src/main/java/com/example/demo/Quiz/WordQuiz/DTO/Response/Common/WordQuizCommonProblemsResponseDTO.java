package com.example.demo.Quiz.WordQuiz.DTO.Response.Common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class WordQuizCommonProblemsResponseDTO {

    private Long quizId;

    private List<WordQuizCommonProblemResponseDTO> wordQuizList = new ArrayList<>();
}
