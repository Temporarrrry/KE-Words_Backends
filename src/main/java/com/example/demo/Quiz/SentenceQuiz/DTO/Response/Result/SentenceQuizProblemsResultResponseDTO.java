package com.example.demo.Quiz.SentenceQuiz.DTO.Response.Result;

import com.example.demo.Quiz.SentenceQuiz.Entity.SentenceQuiz;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
public class SentenceQuizProblemsResultResponseDTO {

    private Long quizId;

    private Long userId;

    private Integer correctCount;

    private Integer totalCount;

    private List<SentenceQuizProblemResultResponseDTO> sentenceQuizProblemResultResponseDTOList;


    @Builder
    public SentenceQuizProblemsResultResponseDTO(Long userId, Integer correctCount, Integer totalCount, List<SentenceQuizProblemResultResponseDTO> sentenceQuizProblemResultResponseDTOList) {
        this.userId = userId;
        this.correctCount = correctCount;
        this.totalCount = totalCount;
        this.sentenceQuizProblemResultResponseDTOList = sentenceQuizProblemResultResponseDTOList;
    }


    public SentenceQuizProblemsResultResponseDTO(SentenceQuiz sentenceQuiz, List<List<String>> originalSentences) {
        this.quizId = sentenceQuiz.getId();
        this.userId = sentenceQuiz.getUserId();
        this.correctCount = sentenceQuiz.getCorrectCount();
        this.totalCount = sentenceQuiz.getTotalCount();

        List<Long> sentenceIds = sentenceQuiz.getSentenceIds();
        List<List<String>> problemSentences = sentenceQuiz.getProblemSentences();

        Optional<List<List<String>>> userAnswers = sentenceQuiz.getUserAnswers();
        List<Boolean> results = sentenceQuiz.getResult();

        List<SentenceQuizProblemResultResponseDTO> sentenceQuizProblemResultResponseDTOList = new ArrayList<>();
        for (int idx = 0; idx < sentenceIds.size(); idx++) {
            sentenceQuizProblemResultResponseDTOList.add(
                    SentenceQuizProblemResultResponseDTO.builder()
                            .sentenceId(sentenceIds.get(idx))
                            .originalSentence(originalSentences.get(idx))
                            .problemSentence(problemSentences.get(idx))
                            .userAnswer((userAnswers.isPresent()) ? userAnswers.get().get(idx) : null)
                            .result(results.get(idx))
                            .build()
            );
        }

        this.sentenceQuizProblemResultResponseDTOList = sentenceQuizProblemResultResponseDTOList;
    }
}
