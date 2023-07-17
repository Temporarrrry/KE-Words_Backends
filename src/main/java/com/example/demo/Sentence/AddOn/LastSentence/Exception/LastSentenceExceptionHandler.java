package com.example.demo.Sentence.AddOn.LastSentence.Exception;

import com.example.demo.Sentence.AddOn.LastSentence.Controller.LastSentenceController;
import com.example.demo.Sentence.Exception.SentenceNotExistException;
import com.example.demo.Word.Exception.WordNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = LastSentenceController.class)
public class LastSentenceExceptionHandler {

    @ExceptionHandler(WordNotExistException.class)
    public ResponseEntity<String> wordNotExist(SentenceNotExistException e) {
        return new ResponseEntity<>("존재하지 않는 문장입니다.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LastSentenceNotExistException.class)
    public ResponseEntity<String> LastWordNotExist(LastSentenceNotExistException e) {
        return new ResponseEntity<>("마지막으로 본 문장이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> unHandledError(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<>("예상치 못한 에러가 발생했습니다.", HttpStatus.NOT_ACCEPTABLE);
    }
}
