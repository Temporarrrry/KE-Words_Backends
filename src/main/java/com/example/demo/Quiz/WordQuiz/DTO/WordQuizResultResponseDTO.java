package com.example.demo.Quiz.WordQuiz.DTO;

import com.example.demo.Quiz.WordQuiz.Entity.WordQuiz;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class WordQuizResultResponseDTO {

    private Long id;

    private Long userId;

    private List<String> english;

    private int score;

    private int count;

    private LocalDate quizDate;

    private List<Boolean> result;

    public WordQuizResultResponseDTO(List<String> english, WordQuiz wordQuiz) {
        this.id = wordQuiz.getId();
        this.userId = wordQuiz.getUserId();
        this.english = english;
        this.quizDate = wordQuiz.getQuizDate();
        this.result = Arrays.stream(wordQuiz.getResult().split("\\|")).map(s -> s.equals("1")).toList();
        this.score = Long.valueOf(Arrays.stream(wordQuiz.getResult().split("")).map(s -> s.equals("1"))
                .filter(Boolean::booleanValue).count()).intValue();
        this.count = Long.valueOf(this.result.size()).intValue();
    }
}
