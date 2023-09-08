package com.example.demo.Quiz.WordQuiz.DTO.Response.Result;

import com.example.demo.Quiz.WordQuiz.Entity.WordQuiz;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Setter
@Getter
@NoArgsConstructor
public class WordQuizProblemsResultResponseDTO {

    private Long quizId;

    private Long userId;

    private Integer correctCount;

    private Integer totalCount;

    private List<WordQuizProblemResultResponseDTO> wordQuizProblemResultResponseDTOList;

    @Builder
    public WordQuizProblemsResultResponseDTO(Long userId, Integer correctCount, Integer totalCount, List<WordQuizProblemResultResponseDTO> wordQuizProblemResultResponseDTOList) {
        this.userId = userId;
        this.correctCount = correctCount;
        this.totalCount = totalCount;
        this.wordQuizProblemResultResponseDTOList = wordQuizProblemResultResponseDTOList;
    }

    public WordQuizProblemsResultResponseDTO(WordQuiz wordQuiz, List<String> englishes, List<List<String>> originalKorean) {
        this.quizId = wordQuiz.getId();
        this.userId = wordQuiz.getUserId();
        this.correctCount = wordQuiz.getCorrectCount();
        this.totalCount = wordQuiz.getTotalCount();

        List<Long> wordIds = wordQuiz.getWordIds();
        List<List<List<String>>> koreanChoices = wordQuiz.getKoreanChoices();
        Optional<List<List<String>>> userAnswers = wordQuiz.getUserAnswers();
        List<Boolean> result = wordQuiz.getResult();

        List<WordQuizProblemResultResponseDTO> wordQuizProblemResultResponseDTOList = new ArrayList<>();
        for (int idx = 0; idx < wordIds.size(); idx++) {
            wordQuizProblemResultResponseDTOList.add(
                    WordQuizProblemResultResponseDTO.builder()
                            .wordId(wordIds.get(idx))
                            .english(englishes.get(idx))
                            .originalKorean(originalKorean.get(idx))
                            .userKoreanAnswer((userAnswers.isPresent()) ? userAnswers.get().get(idx) : null)
                            .koreanChoices(koreanChoices.get(idx))
                            .result(result.get(idx))
                            .build()
            );
        }

        this.wordQuizProblemResultResponseDTOList = wordQuizProblemResultResponseDTOList;
    }
}
