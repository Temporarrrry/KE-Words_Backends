package com.example.demo.Sentence.AddOn.BookmarkSentence.Exception;

import com.example.demo.Common.Exception.RuntimeExceptionWithHttpStatus;
import org.springframework.http.HttpStatus;

public class BookmarkSentenceExistException extends RuntimeExceptionWithHttpStatus {
    public BookmarkSentenceExistException() {
        super("이미 존재하는 북마크 단어입니다.", HttpStatus.CONFLICT);
    }
}
