package com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Meaning.Practice;

import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Meaning.Common.SentenceQuizMeaningCommonProblem;
import com.example.demo.Quiz.SentenceQuiz.Serializer.SentenceKoreanChoicesSerializer;
import com.example.demo.Sentence.Serializer.SentenceKoreanSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SentenceQuizMeaningPracticeProblem {
    private Long sentenceId;

    private List<String> english;

    @JsonSerialize(using = SentenceKoreanChoicesSerializer.class)
    private List<String> koreanChoices;

    @JsonSerialize(using = SentenceKoreanSerializer.class)
    private String originalKorean;

    public SentenceQuizMeaningPracticeProblem(SentenceQuizMeaningCommonProblem sentenceQuizMeaningCommonProblem) {
        this.sentenceId = sentenceQuizMeaningCommonProblem.getSentenceId();
        this.english = sentenceQuizMeaningCommonProblem.getEnglish();
        this.koreanChoices = sentenceQuizMeaningCommonProblem.getKoreanChoices();
        this.originalKorean = sentenceQuizMeaningCommonProblem.getOriginalKorean();
    }
}
