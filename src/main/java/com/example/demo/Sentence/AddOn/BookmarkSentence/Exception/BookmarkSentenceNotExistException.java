package com.example.demo.Sentence.AddOn.BookmarkSentence.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "북마크 문장이 존재하지 않습니다.")
public class BookmarkSentenceNotExistException extends RuntimeException {
    public BookmarkSentenceNotExistException() {
        super();
    }
}
