package com.example.demo.Quiz.SentenceQuiz.DTO;

import com.example.demo.Quiz.SentenceQuiz.Entity.SentenceQuiz;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class SentenceQuizResponseDTO {

    private Long id;

    private Long userId;

    private List<String> originalSentence;

    private List<String> problemSentence;

    private List<String> userAnswer;

    private int score;

    private int count;

    private LocalDate quizDate;

    private List<Boolean> result;

    public SentenceQuizResponseDTO(SentenceQuiz sentenceQuiz, List<String> originalSentence) {
        this.id = sentenceQuiz.getId();
        this.userId = sentenceQuiz.getUserId();
        this.originalSentence = originalSentence;
        this.problemSentence = sentenceQuiz.getProblemSentences()
                .stream().map(strings -> String.join(" ", strings))
                .toList();
        //this.userAnswer = sentenceQuiz.getUserAnswers();//TODO
        this.quizDate = sentenceQuiz.getQuizDate();
        this.result = sentenceQuiz.getResult();
        this.score = sentenceQuiz.getCorrectCount();
        this.count = sentenceQuiz.getTotalCount();
    }
}
