package com.example.demo.Quiz.dto;


import com.example.demo.Quiz.Entity.Quiz;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AllQuizByUserIdResponseDTO {

    private int totalScore;

    private int totalCount;

    private List<QuizResponseDTO> entireQuiz; //TODO page가 list가 됌

    public AllQuizByUserIdResponseDTO(Page<Quiz> entireQuiz) {
        this.totalScore = entireQuiz.stream().mapToInt(Quiz::getScore).sum();
        this.totalCount = entireQuiz.stream().mapToInt(Quiz::getCount).sum();
        this.entireQuiz = entireQuiz.stream().map(QuizResponseDTO::new).toList();
    }
}
