package com.example.demo.Quiz.SentenceQuiz.DTO.Response.Test;

import com.example.demo.Quiz.SentenceQuiz.DTO.Response.Common.CommonSentenceQuizProblem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class TestSentenceQuizProblem {

    private Long sentenceId;

    private List<String> editedEnglish;

    private String korean;

    public TestSentenceQuizProblem(CommonSentenceQuizProblem commonSentenceQuizProblem) {
        this.sentenceId = commonSentenceQuizProblem.getSentenceId();
        this.editedEnglish = commonSentenceQuizProblem.getEditedEnglish();
        this.korean = commonSentenceQuizProblem.getKorean();
    }
}
