package com.example.demo.Quiz.SentenceQuiz.Exception;

import com.example.demo.Common.Exception.RuntimeExceptionWithHttpStatus;
import org.springframework.http.HttpStatus;

public class SentenceQuizNotExistException extends RuntimeExceptionWithHttpStatus {
    public SentenceQuizNotExistException() {
        super("존재하지 않는 퀴즈입니다.", HttpStatus.NOT_FOUND);
    }
}
