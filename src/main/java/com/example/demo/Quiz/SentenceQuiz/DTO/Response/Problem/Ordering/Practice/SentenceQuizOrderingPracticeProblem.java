package com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Ordering.Practice;

import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Ordering.Common.SentenceQuizOrderingCommonProblem;
import com.example.demo.Sentence.Serializer.SentenceKoreanSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
public class SentenceQuizOrderingPracticeProblem {
    private Long sentenceId;

    private List<String> originalEnglish;

    private List<String> shuffledEnglish;

    @JsonSerialize(using = SentenceKoreanSerializer.class)
    private String korean;

    public SentenceQuizOrderingPracticeProblem(SentenceQuizOrderingCommonProblem sentenceQuizOrderingCommonProblem) {
        this.sentenceId = sentenceQuizOrderingCommonProblem.getSentenceId();
        this.originalEnglish = sentenceQuizOrderingCommonProblem.getOriginalEnglish();
        this.shuffledEnglish = sentenceQuizOrderingCommonProblem.getEditedEnglish();
        this.korean = sentenceQuizOrderingCommonProblem.getKorean();
    }
}
