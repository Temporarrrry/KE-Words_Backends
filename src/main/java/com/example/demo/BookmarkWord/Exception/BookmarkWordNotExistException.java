package com.example.demo.BookmarkWord.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "북마크 단어가 존재하지 않습니다.")
public class BookmarkWordNotExistException extends RuntimeException {
    public BookmarkWordNotExistException() {
        super();
    }
}
