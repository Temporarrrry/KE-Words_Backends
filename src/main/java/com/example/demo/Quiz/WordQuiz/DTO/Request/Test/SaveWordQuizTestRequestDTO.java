package com.example.demo.Quiz.WordQuiz.DTO.Request.Test;

import com.example.demo.Quiz.WordQuiz.Entity.WordQuiz;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveWordQuizTestRequestDTO {

    private Long userId;

    private List<SaveWordQuizTestProblemRequestDTO> problems;

    public SaveWordQuizTestRequestDTO(Long userId, List<Long> wordIds, List<List<List<String>>> koreanChoices) {
        this.userId = userId;
        this.problems = IntStream.range(0, wordIds.size())
                .mapToObj(idx -> new SaveWordQuizTestProblemRequestDTO(wordIds.get(idx), koreanChoices.get(idx)))
                .toList();
    }

    public WordQuiz toEntity(Optional<List<List<String>>> userAnswers, List<Boolean> result) {
        return WordQuiz.builder()
                .userId(userId)
                .quizDate(LocalDate.now())
                .wordIds(problems.stream().map(SaveWordQuizTestProblemRequestDTO::getWordId).toList())
                .koreanChoices(problems.stream().map(SaveWordQuizTestProblemRequestDTO::getKoreanChoices).toList())
                .userAnswers(userAnswers)
                .result(result)
                .build();
    }
}
