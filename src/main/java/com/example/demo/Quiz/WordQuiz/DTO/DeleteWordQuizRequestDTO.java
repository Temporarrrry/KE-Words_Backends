package com.example.demo.Quiz.WordQuiz.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeleteWordQuizRequestDTO {
    @NotBlank
    private Long wordQuizId;
}
