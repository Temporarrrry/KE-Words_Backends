package com.example.demo.Quiz.WordQuiz.DTO;

import com.example.demo.Quiz.WordQuiz.Entity.WordQuiz;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class WordQuizResultResponseDTO {

    private Long quizId;

    private Long userId;

    private Integer correctCount;

    private Integer totalCount;

    private List<WordQuizOneProblemResultResponseDTO> wordQuizOneProblemResultResponseDTOList;

    @Builder
    public WordQuizResultResponseDTO(Long userId, Integer correctCount, Integer totalCount, List<WordQuizOneProblemResultResponseDTO> wordQuizOneProblemResultResponseDTOList) {
        this.userId = userId;
        this.correctCount = correctCount;
        this.totalCount = totalCount;
        this.wordQuizOneProblemResultResponseDTOList = wordQuizOneProblemResultResponseDTOList;
    }

    public WordQuizResultResponseDTO(WordQuiz wordQuiz, List<String> englishes, List<List<String>> originalKorean) {
        this.quizId = wordQuiz.getId();
        this.userId = wordQuiz.getUserId();
        this.correctCount = wordQuiz.getCorrectCount();
        this.totalCount = wordQuiz.getTotalCount();

        List<Long> wordIds = wordQuiz.getWordIds();
        List<List<List<String>>> koreanChoices = wordQuiz.getKoreanChoices();
        List<List<String>> userAnswers = wordQuiz.getUserAnswers();
        List<Boolean> result = wordQuiz.getResult();

        List<WordQuizOneProblemResultResponseDTO> wordQuizOneProblemResultResponseDTOList = new ArrayList<>();
        for (int idx = 0; idx < wordIds.size(); idx++) {
            wordQuizOneProblemResultResponseDTOList.add(
                    WordQuizOneProblemResultResponseDTO.builder()
                            .wordId(wordIds.get(idx))
                            .english(englishes.get(idx))
                            .originalKorean(originalKorean.get(idx))
                            .userKoreanAnswer(userAnswers.get(idx))
                            .koreanChoices(koreanChoices.get(idx))
                            .result(result.get(idx))
                            .build()
            );
        }

        this.wordQuizOneProblemResultResponseDTOList = wordQuizOneProblemResultResponseDTOList;
    }
}
