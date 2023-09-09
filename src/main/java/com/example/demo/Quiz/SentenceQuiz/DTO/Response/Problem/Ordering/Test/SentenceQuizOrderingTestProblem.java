package com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Ordering.Test;

import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Ordering.Common.SentenceQuizOrderingCommonProblem;
import com.example.demo.Sentence.Serializer.SentenceKoreanSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SentenceQuizOrderingTestProblem {

    private Long sentenceId;

    private List<String> editedEnglish;

    @JsonSerialize(using = SentenceKoreanSerializer.class)
    private String korean;

    public SentenceQuizOrderingTestProblem(SentenceQuizOrderingCommonProblem sentenceQuizOrderingCommonProblem) {
        this.sentenceId = sentenceQuizOrderingCommonProblem.getSentenceId();
        this.editedEnglish = sentenceQuizOrderingCommonProblem.getEditedEnglish();
        this.korean = sentenceQuizOrderingCommonProblem.getKorean();
    }
}
