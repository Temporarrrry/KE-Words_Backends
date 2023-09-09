package com.example.demo.Quiz.SentenceQuiz.DTO.Response.Result;

import com.example.demo.Sentence.Serializer.SentenceKoreanSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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

    @JsonSerialize(using = SentenceKoreanSerializer.class)
    private String korean;

    private Boolean result;
}
