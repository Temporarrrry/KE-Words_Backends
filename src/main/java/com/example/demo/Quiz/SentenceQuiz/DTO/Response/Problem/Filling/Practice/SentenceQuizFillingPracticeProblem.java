package com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Filling.Practice;

import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Filling.Common.SentenceQuizFillingCommonProblem;
import com.example.demo.Sentence.Serializer.SentenceKoreanSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SentenceQuizFillingPracticeProblem {
    private Long sentenceId;

    private List<String> originalEnglish;

    private List<String> editedEnglish;

    @JsonSerialize(using = SentenceKoreanSerializer.class)
    private String korean;

    public SentenceQuizFillingPracticeProblem(SentenceQuizFillingCommonProblem sentenceQuizFillingCommonProblem) {
        this.sentenceId = sentenceQuizFillingCommonProblem.getSentenceId();
        this.originalEnglish = sentenceQuizFillingCommonProblem.getOriginalEnglish();
        this.editedEnglish = sentenceQuizFillingCommonProblem.getEditedEnglish();
        this.korean = sentenceQuizFillingCommonProblem.getKorean();
    }
}
