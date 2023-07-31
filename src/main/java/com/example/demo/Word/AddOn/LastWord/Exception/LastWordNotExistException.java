package com.example.demo.Word.AddOn.LastWord.Exception;

import com.example.demo.Common.Exception.RuntimeExceptionWithHttpStatus;
import org.springframework.http.HttpStatus;

public class LastWordNotExistException extends RuntimeExceptionWithHttpStatus {
    public LastWordNotExistException() {
        super("마지막으로 본 단어가 존재하지 않습니다.", HttpStatus.NOT_FOUND);
    }
}
