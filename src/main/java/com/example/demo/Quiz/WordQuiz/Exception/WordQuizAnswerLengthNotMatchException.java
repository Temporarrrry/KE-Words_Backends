package com.example.demo.Quiz.WordQuiz.Exception;

import com.example.demo.Common.Exception.RuntimeExceptionWithHttpStatus;
import org.springframework.http.HttpStatus;

public class WordQuizAnswerLengthNotMatchException extends RuntimeExceptionWithHttpStatus {
    public WordQuizAnswerLengthNotMatchException() {
        super("문제와 답의 개수가 다릅니다", HttpStatus.BAD_REQUEST);
    }
}
