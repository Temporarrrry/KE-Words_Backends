package com.example.demo.Quiz.SentenceQuiz.DTO;

import com.example.demo.Quiz.SentenceQuiz.Entity.SentenceQuiz;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class SentenceQuizResultResponseDTO {

    private Long quizId;

    private Long userId;

    private Integer correctCount;

    private Integer totalCount;

    private List<SentenceQuizOneProblemResultResponseDTO> sentenceQuizOneProblemResultResponseDTOList;


    @Builder
    public SentenceQuizResultResponseDTO(Long userId, Integer correctCount, Integer totalCount, List<SentenceQuizOneProblemResultResponseDTO> sentenceQuizOneProblemResultResponseDTOList) {
        this.userId = userId;
        this.correctCount = correctCount;
        this.totalCount = totalCount;
        this.sentenceQuizOneProblemResultResponseDTOList = sentenceQuizOneProblemResultResponseDTOList;
    }


    public SentenceQuizResultResponseDTO(SentenceQuiz sentenceQuiz, List<List<String>> originalSentences) {
        this.quizId = sentenceQuiz.getId();
        this.userId = sentenceQuiz.getUserId();
        this.correctCount = sentenceQuiz.getCorrectCount();
        this.totalCount = sentenceQuiz.getTotalCount();

        List<Long> sentenceIds = sentenceQuiz.getSentenceIds();
        List<List<String>> problemSentences = sentenceQuiz.getProblemSentences();

        List<List<String>> userAnswers = sentenceQuiz.getUserAnswers();
        List<Boolean> results = sentenceQuiz.getResult();

        List<SentenceQuizOneProblemResultResponseDTO> sentenceQuizOneProblemResultResponseDTOList = new ArrayList<>();
        for (int idx = 0; idx < sentenceIds.size(); idx++) {
            sentenceQuizOneProblemResultResponseDTOList.add(
                    SentenceQuizOneProblemResultResponseDTO.builder()
                            .sentenceId(sentenceIds.get(idx))
                            .originalSentence(originalSentences.get(idx))
                            .problemSentence(problemSentences.get(idx))
                            .userAnswer(userAnswers.get(idx))
                            .result(results.get(idx))
                            .build()
            );
        }

        this.sentenceQuizOneProblemResultResponseDTOList = sentenceQuizOneProblemResultResponseDTOList;
    }
}
