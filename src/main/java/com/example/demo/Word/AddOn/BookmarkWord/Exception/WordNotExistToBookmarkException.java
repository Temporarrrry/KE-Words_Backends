package com.example.demo.Word.AddOn.BookmarkWord.Exception;

import com.example.demo.Common.Exception.RuntimeExceptionWithHttpStatus;
import org.springframework.http.HttpStatus;

public class WordNotExistToBookmarkException extends RuntimeExceptionWithHttpStatus {
    public WordNotExistToBookmarkException() {
        super("존재하지 않는 단어를 북마크할 수 없습니다.", HttpStatus.NOT_FOUND);
    }
}
