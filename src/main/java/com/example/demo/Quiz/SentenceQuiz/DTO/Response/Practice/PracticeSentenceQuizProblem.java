package com.example.demo.Quiz.SentenceQuiz.DTO.Response.Practice;

import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Common.CommonSentenceQuizProblem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class PracticeSentenceQuizProblem {
    private Long sentenceId;

    private List<String> originalEnglish;

    private List<String> editedEnglish;

    private String korean;

    public PracticeSentenceQuizProblem(CommonSentenceQuizProblem commonSentenceQuizProblem) {
        this.sentenceId = commonSentenceQuizProblem.getSentenceId();
        this.originalEnglish = commonSentenceQuizProblem.getOriginalEnglish();
        this.editedEnglish = commonSentenceQuizProblem.getEditedEnglish();
        this.korean = commonSentenceQuizProblem.getKorean();
    }
}
