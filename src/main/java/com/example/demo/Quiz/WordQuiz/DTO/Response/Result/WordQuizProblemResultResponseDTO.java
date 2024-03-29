package com.example.demo.Quiz.WordQuiz.DTO.Response.Result;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class WordQuizProblemResultResponseDTO {

    private Long wordId;

    private String english;

    private List<String> originalKorean;

    private List<String> userKoreanAnswer;

    private List<List<String>> koreanChoices;

    private Boolean result;

    @Builder
    public WordQuizProblemResultResponseDTO(Long wordId, String english, List<String> originalKorean, List<String> userKoreanAnswer, List<List<String>> koreanChoices, Boolean result) {
        this.wordId = wordId;
        this.english = english;
        this.originalKorean = originalKorean;
        this.userKoreanAnswer = userKoreanAnswer;
        this.koreanChoices = koreanChoices;
        this.result = result;
    }
}
