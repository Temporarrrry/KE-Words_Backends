package com.example.demo.Word.AddOn.LastWord.Exception;

import com.example.demo.Word.Exception.WordNotExistException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LastWordExceptionHandler {

    @ExceptionHandler(WordNotExistException.class)
    public ResponseEntity<String> wordNotExist(WordNotExistException e) {
        return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
    }

    @ExceptionHandler(LastWordNotExistException.class)
    public ResponseEntity<String> LastWordNotExist(LastWordNotExistException e) {
        return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
    }
}
