package com.example.demo.Quiz.SentenceQuiz.DTO.Response.Result.Meaning;

import com.example.demo.Quiz.SentenceQuiz.Serializer.SentenceKoreanChoicesSerializer;
import com.example.demo.Sentence.Serializer.SentenceKoreanSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SentenceQuizMeaningProblemResult {
    private Long sentenceId;

    private List<String> originalEnglish;

    @JsonSerialize(using = SentenceKoreanChoicesSerializer.class)
    private List<String> koreanChoices;

    @JsonSerialize(using = SentenceKoreanSerializer.class)
    private String korean;

    private Boolean result;
}
