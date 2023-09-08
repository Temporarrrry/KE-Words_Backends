package com.example.demo.Quiz.WordQuiz.DTO.Response.Practice;

import com.example.demo.Quiz.WordQuiz.DTO.Response.Common.WordQuizCommonProblemsResponseDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class WordQuizPracticeProblemsResponseDTO {

    private List<WordQuizPracticeProblemResponseDTO> wordQuizList = new ArrayList<>();

    public WordQuizPracticeProblemsResponseDTO(WordQuizCommonProblemsResponseDTO wordQuizCommonProblemsResponseDTO) {
        this.wordQuizList = wordQuizCommonProblemsResponseDTO
                .getWordQuizList()
                .stream().map(WordQuizPracticeProblemResponseDTO::new)
                .toList();
    }
}
