package com.example.demo.Quiz.WordQuiz.Exception;

import com.example.demo.Common.Exception.RuntimeExceptionWithHttpStatus;
import org.springframework.http.HttpStatus;

public class WordQuizNotExistException extends RuntimeExceptionWithHttpStatus {
    public WordQuizNotExistException() {
        super("존재하지 않는 퀴즈입니다.", HttpStatus.NOT_FOUND);
    }
}
