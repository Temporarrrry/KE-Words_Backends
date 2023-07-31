package com.example.demo.Sentence.AddOn.BookmarkSentence.Exception;

import com.example.demo.Common.Exception.RuntimeExceptionWithHttpStatus;
import org.springframework.http.HttpStatus;

public class BookmarkSentenceNotExistException extends RuntimeExceptionWithHttpStatus {
    public BookmarkSentenceNotExistException() {
        super("존재하지 않는 북마크 단어입니다.", HttpStatus.NOT_FOUND);
    }
}
