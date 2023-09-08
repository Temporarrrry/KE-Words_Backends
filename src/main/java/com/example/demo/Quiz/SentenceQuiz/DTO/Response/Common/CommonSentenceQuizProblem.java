package com.example.demo.Quiz.SentenceQuiz.DTO.Response.Common;

import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonSentenceQuizProblem {
    private Long sentenceId;

    private List<String> originalEnglish;

    private List<String> editedEnglish;

    private String korean;
}
