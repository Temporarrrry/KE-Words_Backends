package com.example.demo.Quiz.SentenceQuiz.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SentenceQuizExceptionHandler {

    @ExceptionHandler(SentenceQuizNotExistException.class)
    public ResponseEntity<String> quizNotExist(SentenceQuizNotExistException e) {
        return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
    }

    @ExceptionHandler(SentenceTooShortException.class)
    public ResponseEntity<String> sentenceTooShort(SentenceTooShortException e) {
        return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
    }

    @ExceptionHandler(SentenceQuizAnswerLengthNotMatchException.class)
    public ResponseEntity<String> answerLengthNotMatch(SentenceQuizAnswerLengthNotMatchException e) {
        return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
    }

    @ExceptionHandler(SentenceQuizAnswerNotMatchException.class)
    public ResponseEntity<String> answerNotMatch(SentenceQuizAnswerNotMatchException e) {
        return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
    }
}
