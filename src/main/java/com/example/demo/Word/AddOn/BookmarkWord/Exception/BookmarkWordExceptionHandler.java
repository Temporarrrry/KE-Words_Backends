package com.example.demo.Word.AddOn.BookmarkWord.Exception;

import com.example.demo.Word.AddOn.BookmarkWord.Controller.BookmarkWordController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = BookmarkWordController.class)
public class BookmarkWordExceptionHandler {

    @ExceptionHandler(WordNotExistToBookmarkException.class)
    public ResponseEntity<String> wordNotExistToBookmark(WordNotExistToBookmarkException e) {
        return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
    }

    @ExceptionHandler(BookmarkWordExistException.class)
    public ResponseEntity<String> wordExist(BookmarkWordExistException e) {
        return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
    }

    @ExceptionHandler(BookmarkWordNotExistException.class)
    public ResponseEntity<String> wordNotExist(BookmarkWordNotExistException e) {
        return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
    }
}
