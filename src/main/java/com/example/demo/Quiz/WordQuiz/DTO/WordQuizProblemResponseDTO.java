package com.example.demo.Quiz.WordQuiz.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class WordQuizProblemResponseDTO {

    private Long id;

    private Long userId;

    private List<String> english;

    private List<List<List<String>>> koreanChoice;

    private List<List<String>> answer;

    public WordQuizProblemResponseDTO() {
        this.koreanChoice = new ArrayList<>();
    }

    /*public QuizEnglishProblemResponseDTO(Quiz quiz) {
        this.id = quiz.getId();
        this.userId = quiz.getUserId();
        this.english = Arrays.asList(quiz.getEnglish().split("\\|"));
        this.result = Arrays.stream(quiz.getResult().split("")).map(s -> s.equals("1")).toList();
    }*/
}
