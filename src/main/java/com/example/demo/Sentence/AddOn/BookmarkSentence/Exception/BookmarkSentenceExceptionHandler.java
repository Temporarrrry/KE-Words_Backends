package com.example.demo.Sentence.AddOn.BookmarkSentence.Exception;

import com.example.demo.Sentence.AddOn.BookmarkSentence.Controller.BookmarkSentenceController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = BookmarkSentenceController.class)
public class BookmarkSentenceExceptionHandler {

    @ExceptionHandler(SentenceNotExistToBookmarkException.class)
    public ResponseEntity<String> wordNotExistToBookmark(SentenceNotExistToBookmarkException e) {
        return new ResponseEntity<>("존재하지 않는 단어를 북마크할 수 없습니다.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookmarkSentenceExistException.class)
    public ResponseEntity<String> wordExist(BookmarkSentenceExistException e) {
        return new ResponseEntity<>("이미 존재하는 북마크 단어입니다.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookmarkSentenceNotExistException.class)
    public ResponseEntity<String> wordNotExist(BookmarkSentenceNotExistException e) {
        return new ResponseEntity<>("존재하지 않는 북마크 단어입니다.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> unHandledError(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<>("예상치 못한 에러가 발생했습니다.", HttpStatus.NOT_ACCEPTABLE);
    }
}
