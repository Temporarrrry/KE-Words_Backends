package com.example.demo.Quiz.WordQuiz.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WordQuizExceptionHandler {

    @ExceptionHandler(WordQuizNotExistException.class)
    public ResponseEntity<String> quizNotExist(WordQuizNotExistException e) {
        return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
    }
}
