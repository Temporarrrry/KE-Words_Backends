package com.example.demo.Quiz.WordQuiz.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class WordQuizProblemsResponseDTO {

    private List<WordQuizProblemResponseDTO> wordQuizList = new ArrayList<>();

    /*public QuizEnglishProblemResponseDTO(Quiz quiz) {
        this.id = quiz.getId();
        this.userId = quiz.getUserId();
        this.english = Arrays.asList(quiz.getEnglish().split("\\|"));
        this.result = Arrays.stream(quiz.getResult().split("")).map(s -> s.equals("1")).toList();
    }*/
}
