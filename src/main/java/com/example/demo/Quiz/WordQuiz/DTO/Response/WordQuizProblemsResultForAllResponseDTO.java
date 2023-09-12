package com.example.demo.Quiz.WordQuiz.DTO.Response;

import com.example.demo.Quiz.WordQuiz.Entity.WordQuiz;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class WordQuizProblemsResultForAllResponseDTO {

    private Long quizId;

    private Integer correctCount;

    private Integer totalCount;

    public WordQuizProblemsResultForAllResponseDTO(WordQuiz wordQuiz) {
        this.quizId = wordQuiz.getId();
        this.correctCount = wordQuiz.getCorrectCount();
        this.totalCount = wordQuiz.getTotalCount();
    }
}
