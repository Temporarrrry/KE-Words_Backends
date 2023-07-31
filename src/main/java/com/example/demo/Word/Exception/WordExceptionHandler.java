package com.example.demo.Word.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = WordExceptionHandler.class)
public class WordExceptionHandler {

    @ExceptionHandler(WordExistException.class)
    public ResponseEntity<String> wordExist(WordExistException e) {
        return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
    }

    @ExceptionHandler(WordNotExistException.class)
    public ResponseEntity<String> wordNotExist(WordNotExistException e) {
        return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
    }
}
