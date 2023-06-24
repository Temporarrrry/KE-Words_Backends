package com.example.demo.Quiz.dto;

import com.example.demo.Quiz.Entity.Quiz;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Setter
@Getter
public class QuizProblemResponseDTO {

    @NotBlank
    private Long id;

    @NotBlank
    private Long userId;

    @NotBlank
    private List<String> english;

    @NotBlank
    private List<List<List<String>>> koreanChoice;

    private List<Boolean> result;

    public QuizProblemResponseDTO() {
        this.koreanChoice = new ArrayList<>();
    }

    public QuizProblemResponseDTO(Quiz quiz) {
        this.id = quiz.getId();
        this.userId = quiz.getUserId();
        this.english = Arrays.asList(quiz.getEnglish().split("\\|")) ;
        this.result = Arrays.stream(quiz.getResult().split("")).map(s -> s.equals("1")).toList();
    }
}
