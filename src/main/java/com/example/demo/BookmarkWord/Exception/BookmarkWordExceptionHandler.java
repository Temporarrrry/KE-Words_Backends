package com.example.demo.BookmarkWord.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BookmarkWordExceptionHandler {

    @ExceptionHandler(WordNotExistToBookmarkException.class)
    public ResponseEntity<String> wordNotExistToBookmark(WordNotExistToBookmarkException e) {
        return new ResponseEntity<>("존재하지 않는 단어를 북마크할 수 없습니다.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookmarkWordExistException.class)
    public ResponseEntity<String> wordExist(BookmarkWordExistException e) {
        return new ResponseEntity<>("이미 존재하는 북마크 단어입니다.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookmarkWordNotExistException.class)
    public ResponseEntity<String> wordNotExist(BookmarkWordNotExistException e) {
        return new ResponseEntity<>("존재하지 않는 북마크 단어입니다.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> unHandledError(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<>("예상치 못한 에러가 발생했습니다.", HttpStatus.NOT_ACCEPTABLE);
    }
}
