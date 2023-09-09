package com.example.demo.Quiz.SentenceQuiz.DTO.Response.Problem.Ordering.Common;

import com.example.demo.Sentence.Serializer.SentenceKoreanSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SentenceQuizOrderingCommonProblem {
    private Long sentenceId;

    private List<String> originalEnglish;

    private List<String> editedEnglish;

    @JsonSerialize(using = SentenceKoreanSerializer.class)
    private String korean;
}
