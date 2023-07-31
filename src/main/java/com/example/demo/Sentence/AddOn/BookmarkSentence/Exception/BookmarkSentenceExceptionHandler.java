package com.example.demo.Sentence.AddOn.BookmarkSentence.Exception;

import com.example.demo.Sentence.AddOn.BookmarkSentence.Controller.BookmarkSentenceController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = BookmarkSentenceController.class)
public class BookmarkSentenceExceptionHandler {

    @ExceptionHandler(SentenceNotExistToBookmarkException.class)
    public ResponseEntity<String> wordNotExistToBookmark(SentenceNotExistToBookmarkException e) {
        return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
    }

    @ExceptionHandler(BookmarkSentenceExistException.class)
    public ResponseEntity<String> wordExist(BookmarkSentenceExistException e) {
        return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
    }

    @ExceptionHandler(BookmarkSentenceNotExistException.class)
    public ResponseEntity<String> wordNotExist(BookmarkSentenceNotExistException e) {
        return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
    }
}
