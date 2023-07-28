package com.example.demo.Sentence.AddOn.BookmarkSentence.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "북마크할 문장이 존재하지 않습니다.")
public class SentenceNotExistToBookmarkException extends RuntimeException {
    public SentenceNotExistToBookmarkException() {
        super();
    }
}
