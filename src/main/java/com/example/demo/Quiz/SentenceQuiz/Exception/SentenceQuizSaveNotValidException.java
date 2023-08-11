package com.example.demo.Quiz.SentenceQuiz.Exception;

import com.example.demo.Common.Exception.RuntimeExceptionWithHttpStatus;
import org.springframework.http.HttpStatus;

public class SentenceQuizSaveNotValidException extends RuntimeExceptionWithHttpStatus {
    public SentenceQuizSaveNotValidException() {
        super("비유효한 퀴즈 요청입니다.", HttpStatus.BAD_REQUEST);
    }
}
