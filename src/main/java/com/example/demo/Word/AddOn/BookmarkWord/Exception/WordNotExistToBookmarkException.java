package com.example.demo.Word.AddOn.BookmarkWord.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "북마크할 단어가 존재하지 않습니다.")
public class WordNotExistToBookmarkException extends RuntimeException {
    public WordNotExistToBookmarkException() {
        super();
    }
}
