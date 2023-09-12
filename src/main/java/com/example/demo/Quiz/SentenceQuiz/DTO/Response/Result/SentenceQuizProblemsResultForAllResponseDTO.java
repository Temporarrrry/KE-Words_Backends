package com.example.demo.Quiz.SentenceQuiz.DTO.Response.Result;

import com.example.demo.Quiz.SentenceQuiz.Entity.SentenceQuiz;
import com.example.demo.Quiz.SentenceQuiz.Entity.SentenceQuizType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SentenceQuizProblemsResultForAllResponseDTO {

    private Long quizId;

    private SentenceQuizType type;

    private Integer correctCount;

    private Integer totalCount;

    public SentenceQuizProblemsResultForAllResponseDTO(SentenceQuiz sentenceQuiz) {
        this.quizId = sentenceQuiz.getId();
        this.type = sentenceQuiz.getType();
        this.correctCount = sentenceQuiz.getCorrectCount();
        this.totalCount = sentenceQuiz.getTotalCount();
    }
}
