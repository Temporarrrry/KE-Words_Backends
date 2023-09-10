package com.example.demo.Quiz.WordQuiz.DTO.Response.Practice;

import com.example.demo.Quiz.WordQuiz.DTO.Response.Common.WordQuizCommonProblemResponseDTO;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WordQuizPracticeProblemResponseDTO {

    private Long wordId;

    private String english;

    private List<String> originalKorean;

    private List<List<String>> koreanChoices;

    public WordQuizPracticeProblemResponseDTO(WordQuizCommonProblemResponseDTO wordQuizCommonProblemResponseDTO) {
        this.wordId = wordQuizCommonProblemResponseDTO.getWordId();
        this.english = wordQuizCommonProblemResponseDTO.getEnglish();
        this.originalKorean = wordQuizCommonProblemResponseDTO.getOriginalKorean();
        this.koreanChoices = wordQuizCommonProblemResponseDTO.getKoreanChoices();
    }
}
