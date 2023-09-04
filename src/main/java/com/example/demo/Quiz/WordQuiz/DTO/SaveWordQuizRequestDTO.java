package com.example.demo.Quiz.WordQuiz.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveWordQuizRequestDTO {
    private Long wordId;

    private List<List<String>> problemKoreans;

    private List<String> userKoreanAnswer;
}
