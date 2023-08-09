package com.example.demo.Quiz.SentenceQuiz.DTO.FillingQuiz;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class FillingQuizProblemsResponseDTO {

    private List<FillingQuizProblem> fillingQuizProblems = new ArrayList<>();
}
