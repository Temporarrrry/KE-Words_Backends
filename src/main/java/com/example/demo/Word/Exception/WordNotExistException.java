package com.example.demo.Word.Exception;

import com.example.demo.Common.Exception.RuntimeExceptionWithHttpStatus;
import org.springframework.http.HttpStatus;

public class WordNotExistException extends RuntimeExceptionWithHttpStatus {
    public WordNotExistException() {
        super("존재하지 않는 단어입니다.", HttpStatus.NOT_FOUND);
    }
}
