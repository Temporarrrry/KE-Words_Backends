package com.example.demo.Quiz.WordQuiz.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenerateWordQuizRequestDTO {

    private Long userId;

    private Integer count;

    private Boolean isTest;
}
