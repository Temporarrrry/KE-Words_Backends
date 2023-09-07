package com.example.demo.Quiz.WordQuiz.DTO.Request.Test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveWordQuizTestProblemRequestDTO {
    private Long wordId;

    private List<List<String>> koreanChoices;
}
