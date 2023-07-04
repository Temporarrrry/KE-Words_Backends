package com.example.demo.Quiz.dto;

import com.example.demo.Quiz.Entity.Quiz;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuizRequestDTO {

    @NotBlank
    private Long userId;

    private LocalDate quizDate;

    @NotBlank
    private List<String> english;

    private List<Boolean> result;

    public Quiz toEntity(){
        return Quiz.builder()
                .userId(userId)
                .quizDate(LocalDate.now())
                .english(english)
                .result(result)
                .build();
    }
}
