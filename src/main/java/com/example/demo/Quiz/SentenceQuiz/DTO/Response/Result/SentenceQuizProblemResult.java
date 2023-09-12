package com.example.demo.Quiz.SentenceQuiz.DTO.Response.Result;

import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SentenceQuizProblemResult {
    private Long sentenceId;

    private List<String> english;

    private List<String> editedEnglishOrKoreanChoices;

    private String originalKorean;

    private List<String> userAnswer;

    private Boolean result;
}
