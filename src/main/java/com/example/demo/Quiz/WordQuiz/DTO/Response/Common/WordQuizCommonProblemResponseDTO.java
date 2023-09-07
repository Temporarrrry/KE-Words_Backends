package com.example.demo.Quiz.WordQuiz.DTO.Response.Common;

import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WordQuizCommonProblemResponseDTO {

    private Long wordId;

    private String english;

    private List<String> originalKorean;

    private List<List<String>> koreanChoice;
}
