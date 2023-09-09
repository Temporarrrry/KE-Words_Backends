package com.example.demo.Quiz.WordQuiz.DTO.Request.Grade;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GradeWordQuizTestRequestDTO {

    private List<GradeWordQuizTestProblemRequestDTO> userAnswers;
}
