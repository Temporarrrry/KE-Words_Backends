package com.example.demo.Quiz.SentenceQuiz.Exception;

import com.example.demo.Common.Exception.RuntimeExceptionWithHttpStatus;
import org.springframework.http.HttpStatus;

public class SentenceQuizAnswerNotMatchException extends RuntimeExceptionWithHttpStatus {
    public SentenceQuizAnswerNotMatchException() {
        super("문제와 답이 매칭되지 않습니다.", HttpStatus.BAD_REQUEST);
    }
}
