package com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Filling.Test;

import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Filling.Common.SentenceQuizFillingCommonProblem;
import com.example.demo.Sentence.Serializer.SentenceKoreanSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
public class SentenceQuizFillingTestProblem {

    private Long sentenceId;

    private List<String> editedEnglish;

    @JsonSerialize(using = SentenceKoreanSerializer.class)
    private String korean;

    public SentenceQuizFillingTestProblem(SentenceQuizFillingCommonProblem sentenceQuizFillingCommonProblem) {
        this.sentenceId = sentenceQuizFillingCommonProblem.getSentenceId();
        this.editedEnglish = sentenceQuizFillingCommonProblem.getEditedEnglish();
        this.korean = sentenceQuizFillingCommonProblem.getKorean();
    }
}
