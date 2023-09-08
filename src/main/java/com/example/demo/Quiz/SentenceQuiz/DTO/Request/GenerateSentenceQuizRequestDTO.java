package com.example.demo.Quiz.SentenceQuiz.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenerateSentenceQuizRequestDTO {

    private Long userId;

    private Integer count;

    private Boolean isTest;
}
