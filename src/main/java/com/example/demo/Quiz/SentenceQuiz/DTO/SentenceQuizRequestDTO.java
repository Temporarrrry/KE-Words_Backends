package com.example.demo.Quiz.SentenceQuiz.DTO;

import com.example.demo.Quiz.SentenceQuiz.Entity.SentenceQuiz;
import com.example.demo.Quiz.SentenceQuiz.Entity.SentenceQuizType;
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

    private SentenceQuizType type;

    private List<SaveSentenceQuizTestProblemRequestDTO> problems;

    public SentenceQuizRequestDTO(Long userId, SentenceQuizType type, List<Long> sentenceIds, List<List<String>> problemSentencesOrKoreanChoices) {
        this.userId = userId;
        this.type = type;
        this.problems = IntStream.range(0, sentenceIds.size())
                .mapToObj(idx -> new SaveSentenceQuizTestProblemRequestDTO(sentenceIds.get(idx), problemSentencesOrKoreanChoices.get(idx)))
                .toList();
    }

    public SentenceQuiz toEntity(Optional<List<List<String>>> userAnswers, List<Boolean> result) {
        return SentenceQuiz.builder()
                .userId(userId)
                .quizDate(LocalDate.now())
                .sentenceQuizType(type)
                .sentenceIds(problems.stream().map(SaveSentenceQuizTestProblemRequestDTO::getSentenceId).toList())
                .problemSentencesOrKoreanChoices(problems.stream().map(SaveSentenceQuizTestProblemRequestDTO::getProblemSentence).toList())
                .userAnswers(userAnswers)
                .result(result)
                .build();
    }
}
