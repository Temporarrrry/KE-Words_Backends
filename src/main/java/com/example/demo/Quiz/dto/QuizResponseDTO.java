package com.example.demo.Quiz.dto;

import com.example.demo.Quiz.Entity.Quiz;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class QuizResponseDTO {

    @NotBlank
    private Long id;

    @NotBlank
    private Long userId;

    @NotBlank
    private List<String> english;

    private int score;

    private int count;

    private LocalDate date;

    private List<Boolean> result;

    public QuizResponseDTO(Quiz quiz) {
        this.id = quiz.getId();
        this.userId = quiz.getUserId();
        this.english = Arrays.asList(quiz.getEnglish().split("\\|")) ;
        this.date = quiz.getDate();
        this.result = Arrays.stream(quiz.getResult().split("\\|")).map(s -> s.equals("1")).toList();
        this.score = Long.valueOf(Arrays.stream(quiz.getResult().split("")).map(s -> s.equals("1"))
                .filter(Boolean::booleanValue).count()).intValue();
        this.count = Long.valueOf(this.result.size()).intValue();
    }
}
