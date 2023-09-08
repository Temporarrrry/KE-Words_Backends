package com.example.demo.Quiz.SentenceQuiz.DTO;

import com.example.demo.Quiz.SentenceQuiz.Entity.SentenceQuiz;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class SentenceQuizRequestDTO {

    private Long userId;

    private List<SaveSentenceQuizTestProblemRequestDTO> problems;

    public SentenceQuizRequestDTO(Long userId, List<Long> sentenceIds, List<List<String>> problemSentences) {
        this.userId = userId;
        this.problems = IntStream.range(0, sentenceIds.size())
                .mapToObj(idx -> new SaveSentenceQuizTestProblemRequestDTO(sentenceIds.get(idx), problemSentences.get(idx)))
                .toList();
    }

    public SentenceQuiz toEntity(Optional<List<List<String>>> userAnswers, List<Boolean> result) {
        return SentenceQuiz.builder()
                .userId(userId)
                .quizDate(LocalDate.now())
                .sentenceIds(problems.stream().map(SaveSentenceQuizTestProblemRequestDTO::getSentenceId).toList())
                .problemSentences(problems.stream().map(SaveSentenceQuizTestProblemRequestDTO::getProblemSentence).toList())
                .userAnswers(userAnswers)
                .result(result)
                .build();
    }
}
