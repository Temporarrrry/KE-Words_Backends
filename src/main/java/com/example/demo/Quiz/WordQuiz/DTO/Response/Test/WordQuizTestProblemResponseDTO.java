package com.example.demo.Quiz.WordQuiz.DTO.Response.Test;

import com.example.demo.Quiz.WordQuiz.DTO.Response.Common.WordQuizCommonProblemResponseDTO;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WordQuizTestProblemResponseDTO {

    private Long wordId;

    private String english;

    private List<List<String>> koreanChoice;

    public WordQuizTestProblemResponseDTO(WordQuizCommonProblemResponseDTO wordQuizCommonProblemResponseDTO) {
        this.wordId = wordQuizCommonProblemResponseDTO.getWordId();
        this.english = wordQuizCommonProblemResponseDTO.getEnglish();
        this.koreanChoice = wordQuizCommonProblemResponseDTO.getKoreanChoice();
    }
}
