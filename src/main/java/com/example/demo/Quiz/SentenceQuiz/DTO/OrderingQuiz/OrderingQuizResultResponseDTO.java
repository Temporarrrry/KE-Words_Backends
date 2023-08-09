package com.example.demo.Quiz.SentenceQuiz.DTO.OrderingQuiz;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor

public class OrderingQuizResultResponseDTO {

    private Long id;

    private Long userId;

    List<OrderingQuizResult> orderingQuizResults;
}
