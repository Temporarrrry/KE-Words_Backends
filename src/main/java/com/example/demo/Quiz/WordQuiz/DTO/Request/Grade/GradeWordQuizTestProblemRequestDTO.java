package com.example.demo.Quiz.WordQuiz.DTO.Request.Grade;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GradeWordQuizTestProblemRequestDTO {
    private Long wordId;

    private List<String> userKoreanAnswer;
}
