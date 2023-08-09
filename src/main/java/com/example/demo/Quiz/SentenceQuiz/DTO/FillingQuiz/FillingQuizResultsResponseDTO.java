package com.example.demo.Quiz.SentenceQuiz.DTO.FillingQuiz;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class FillingQuizResultsResponseDTO {

    private Long id;

    private Long userId;

    List<FillingQuizResult> fillingQuizResults;
}
