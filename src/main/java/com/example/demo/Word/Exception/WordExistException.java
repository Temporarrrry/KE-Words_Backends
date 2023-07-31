package com.example.demo.Word.Exception;

import com.example.demo.Common.Exception.RuntimeExceptionWithHttpStatus;
import org.springframework.http.HttpStatus;

public class WordExistException extends RuntimeExceptionWithHttpStatus {
    public WordExistException() {
        super("이미 존재하는 단어입니다.", HttpStatus.CONFLICT);
    }
}
