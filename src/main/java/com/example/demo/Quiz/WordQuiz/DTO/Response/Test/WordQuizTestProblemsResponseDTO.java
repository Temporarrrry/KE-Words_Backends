package com.example.demo.Quiz.WordQuiz.DTO.Response.Test;

import com.example.demo.Quiz.WordQuiz.DTO.Response.Common.WordQuizCommonProblemsResponseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class WordQuizTestProblemsResponseDTO {

    Long quizId;

    private List<WordQuizTestProblemResponseDTO> wordQuizList = new ArrayList<>();

    public WordQuizTestProblemsResponseDTO(WordQuizCommonProblemsResponseDTO wordQuizCommonProblemsResponseDTO) {
        this.quizId = wordQuizCommonProblemsResponseDTO.getQuizId();

        this.wordQuizList = wordQuizCommonProblemsResponseDTO
                .getWordQuizList()
                .stream().map(WordQuizTestProblemResponseDTO::new)
                .toList();
    }
}
