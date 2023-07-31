package com.example.demo.Word.AddOn.BookmarkWord.Exception;

import com.example.demo.Common.Exception.RuntimeExceptionWithHttpStatus;
import org.springframework.http.HttpStatus;

public class BookmarkWordNotExistException extends RuntimeExceptionWithHttpStatus {
    public BookmarkWordNotExistException() {
        super("존재하지 않는 북마크 단어입니다.", HttpStatus.NOT_FOUND);
    }
}
