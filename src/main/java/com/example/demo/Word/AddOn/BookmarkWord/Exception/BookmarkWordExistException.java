package com.example.demo.Word.AddOn.BookmarkWord.Exception;

import com.example.demo.Common.Exception.RuntimeExceptionWithHttpStatus;
import org.springframework.http.HttpStatus;

public class BookmarkWordExistException extends RuntimeExceptionWithHttpStatus {
    public BookmarkWordExistException() {
        super("이미 존재하는 북마크 단어입니다.", HttpStatus.CONFLICT);
    }
}
