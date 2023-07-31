package com.example.demo.Quiz.SentenceQuiz.Exception;

import com.example.demo.Common.Exception.RuntimeExceptionWithHttpStatus;
import org.springframework.http.HttpStatus;

public class SentenceTooShortException extends RuntimeExceptionWithHttpStatus {
    public SentenceTooShortException() {
        super("문장이 너무 짧습니다.", HttpStatus.BAD_REQUEST);
    }
}
