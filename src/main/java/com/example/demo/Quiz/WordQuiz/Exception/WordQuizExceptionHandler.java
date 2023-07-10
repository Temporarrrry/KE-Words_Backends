package com.example.demo.Quiz.WordQuiz.Exception;

import com.example.demo.Quiz.WordQuiz.Controller.WordQuizController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = WordQuizController.class)
public class WordQuizExceptionHandler {

    @ExceptionHandler(WordQuizNotExistException.class)
    public ResponseEntity<String> quizNotExist(WordQuizNotExistException e) {
        return new ResponseEntity<>("존재하지 않는 퀴즈입니다.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> unHandledError(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<>("예상치 못한 에러가 발생했습니다.", HttpStatus.NOT_ACCEPTABLE);
    }
}
