package com.example.demo.Quiz.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class QuizKoreanProblemResponseDTO {

    @NotBlank
    private Long id;

    @NotBlank
    private Long userId;

    @NotBlank
    private List<List<String>> englishChoice;

    @NotBlank
    private List<List<String>> korean;

    private List<Boolean> result;

    private List<String> answer;

    public QuizKoreanProblemResponseDTO() {
        this.englishChoice = new ArrayList<>();
    }

    /*public QuizKoreanProblemResponseDTO(Quiz quiz) {
        this.id = quiz.getId();
        this.userId = quiz.getUserId();
        this.englishChoice = Arrays.asList(quiz.getEnglish().split("\\|"));
        this.result = Arrays.stream(quiz.getResult().split("")).map(s -> s.equals("1")).toList();
    }*/
}
