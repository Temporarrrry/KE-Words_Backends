package com.example.demo.Word.AddOn.LastWord.Exception;

import com.example.demo.Word.AddOn.LastWord.Controller.LastWordController;
import com.example.demo.Word.Exception.WordNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = LastWordController.class)
public class LastWordExceptionHandler {

    @ExceptionHandler(WordNotExistException.class)
    public ResponseEntity<String> wordNotExist(WordNotExistException e) {
        return new ResponseEntity<>("존재하지 않는 단어입니다.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LastWordNotExistException.class)
    public ResponseEntity<String> LastWordNotExist(LastWordNotExistException e) {
        return new ResponseEntity<>("마지막으로 본 단어가 존재하지 않습니다.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> unHandledError(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<>("예상치 못한 에러가 발생했습니다.", HttpStatus.NOT_ACCEPTABLE);
    }
}
