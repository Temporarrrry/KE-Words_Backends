package com.example.demo.Sentence.AddOn.LastSentence.Exception;

import com.example.demo.Sentence.Exception.SentenceNotExistException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LastSentenceExceptionHandler {

    @ExceptionHandler(SentenceNotExistException.class)
    public ResponseEntity<String> sentenceNotExist(SentenceNotExistException e) {
        return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
    }

    @ExceptionHandler(LastSentenceNotExistException.class)
    public ResponseEntity<String> LastSentenceNotExist(LastSentenceNotExistException e) {
        return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
    }
}
