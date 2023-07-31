package com.example.demo.Sentence.Exception;

import com.example.demo.Common.Exception.RuntimeExceptionWithHttpStatus;
import org.springframework.http.HttpStatus;

public class SentenceExistException extends RuntimeExceptionWithHttpStatus {
    public SentenceExistException() {
        super("이미 존재하는 문장입니다.", HttpStatus.CONFLICT);
    }
}
