package com.example.demo.Quiz.WordQuiz.Exception;

import com.example.demo.Common.Exception.RuntimeExceptionWithHttpStatus;
import org.springframework.http.HttpStatus;

public class WordQuizAnswerNotMatchException extends RuntimeExceptionWithHttpStatus {
    public WordQuizAnswerNotMatchException() {
        super("문제와 답이 매칭되지 않습니다.", HttpStatus.BAD_REQUEST);
    }
}
