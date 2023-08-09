package com.example.demo.Quiz.WordQuiz.DTO;

import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WordQuizProblemResponseDTO {

    private Long wordId;

    private String english;

    private List<List<String>> koreanChoice;
}
