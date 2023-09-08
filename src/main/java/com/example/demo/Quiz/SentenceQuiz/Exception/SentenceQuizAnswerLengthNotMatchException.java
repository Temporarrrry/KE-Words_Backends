package com.example.demo.Quiz.SentenceQuiz.Exception;

import com.example.demo.Common.Exception.RuntimeExceptionWithHttpStatus;
import org.springframework.http.HttpStatus;

public class SentenceQuizAnswerLengthNotMatchException extends RuntimeExceptionWithHttpStatus {
    public SentenceQuizAnswerLengthNotMatchException() {
        super("문제와 답의 개수가 다릅니다", HttpStatus.BAD_REQUEST);
    }
}
