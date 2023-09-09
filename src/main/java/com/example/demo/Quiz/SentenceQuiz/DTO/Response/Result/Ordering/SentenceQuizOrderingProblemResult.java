package com.example.demo.Quiz.SentenceQuiz.DTO.Response.Result.Ordering;

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
public class SentenceQuizOrderingProblemResult {
    private Long sentenceId;

    private List<String> originalEnglish;

    private List<String> editedEnglish;

    @JsonSerialize(using = SentenceKoreanSerializer.class)
    private String korean;

    private Boolean result;
}
