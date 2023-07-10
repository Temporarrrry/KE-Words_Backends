package com.example.demo.Sentence.Exception;

import com.example.demo.Sentence.Controller.SentenceController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = SentenceController.class)
public class SentenceExceptionHandler {

    @ExceptionHandler(SentenceExistException.class)
    public ResponseEntity<String> sentenceExist(SentenceExistException e) {
        return new ResponseEntity<>("이미 존재하는 문장입니다.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SentenceNotExistException.class)
    public ResponseEntity<String> sentenceNotExist(SentenceNotExistException e) {
        return new ResponseEntity<>("존재하지 않는 문장입니다.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> unHandledError(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<>("예상치 못한 에러가 발생했습니다.", HttpStatus.NOT_ACCEPTABLE);
    }
}
