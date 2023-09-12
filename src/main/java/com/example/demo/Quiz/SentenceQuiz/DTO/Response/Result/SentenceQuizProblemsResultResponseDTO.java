package com.example.demo.Quiz.SentenceQuiz.DTO.Response.Result;

import com.example.demo.Quiz.SentenceQuiz.Entity.SentenceQuiz;
import com.example.demo.Quiz.SentenceQuiz.Entity.SentenceQuizType;
import com.example.demo.Sentence.DTO.SentenceResponseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Setter
@Getter
@NoArgsConstructor
public class SentenceQuizProblemsResultResponseDTO {

    private Long quizId;

    private Long userId;

    private SentenceQuizType type;

    private Integer correctCount;

    private Integer totalCount;

    private List<SentenceQuizProblemResult> problemResults = new ArrayList<>();

    public SentenceQuizProblemsResultResponseDTO(SentenceQuiz sentenceQuiz,
                                                 List<SentenceResponseDTO> sentenceResponseDTOS) {
        this.quizId = sentenceQuiz.getId();
        this.userId = sentenceQuiz.getUserId();
        this.type = sentenceQuiz.getType();
        this.correctCount = sentenceQuiz.getCorrectCount();
        this.totalCount = sentenceQuiz.getTotalCount();

        for (int i = 0; i < sentenceQuiz.getProblemSentencesOrKoreanChoices().size(); i++) {
            Optional<List<List<String>>> userAnswers = sentenceQuiz.getUserAnswers();

            SentenceQuizProblemResult sentenceQuizProblemResult = SentenceQuizProblemResult.builder()
                    .sentenceId(sentenceQuiz.getSentenceIds().get(i))
                    .english(sentenceResponseDTOS.get(i).getEnglish())
                    .editedEnglishOrKoreanChoices(sentenceQuiz.getProblemSentencesOrKoreanChoices().get(i))
                    .originalKorean(sentenceResponseDTOS.get(i).getKorean())
                    .userAnswer((userAnswers.isPresent()) ? userAnswers.get().get(i) : null)
                    .result(sentenceQuiz.getResult().get(i))
                    .build();

            this.problemResults.add(sentenceQuizProblemResult);
        }
    }
}
