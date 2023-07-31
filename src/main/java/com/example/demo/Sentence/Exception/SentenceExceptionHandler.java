package com.example.demo.Sentence.Exception;

import com.example.demo.Sentence.Controller.SentenceController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = SentenceController.class)
public class SentenceExceptionHandler {

    @ExceptionHandler(SentenceExistException.class)
    public ResponseEntity<String> sentenceExist(SentenceExistException e) {
        return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
    }

    @ExceptionHandler(SentenceNotExistException.class)
    public ResponseEntity<String> sentenceNotExist(SentenceNotExistException e) {
        return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
    }
}
