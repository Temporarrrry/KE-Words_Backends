package com.example.demo.Quiz.SentenceQuiz.DTO;

import com.example.demo.Quiz.SentenceQuiz.Entity.SentenceQuiz;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class SentenceQuizResponseDTO {

    private Long id;

    private Long userId;

    private List<String> english;

    private int score;

    private int count;

    private LocalDate quizDate;

    private List<Boolean> result;

    public SentenceQuizResponseDTO(SentenceQuiz sentenceQuiz, List<String> english) {
        this.id = sentenceQuiz.getId();
        this.userId = sentenceQuiz.getUserId();
        this.english = english;
        this.quizDate = sentenceQuiz.getQuizDate();
        this.result = Arrays.stream(sentenceQuiz.getResult().split("")).map(s -> s.equals("1")).toList();
        this.score = Long.valueOf(Arrays.stream(sentenceQuiz.getResult().split("")).map(s -> s.equals("1"))
                .filter(Boolean::booleanValue).count()).intValue();
        this.count = Long.valueOf(this.result.size()).intValue();
    }
}
