package com.example.demo.Sentence.Exception;

import com.example.demo.Common.Exception.RuntimeExceptionWithHttpStatus;
import org.springframework.http.HttpStatus;

public class SentenceNotExistException extends RuntimeExceptionWithHttpStatus {
    public SentenceNotExistException() {
        super("존재하지 않는 문장입니다.", HttpStatus.NOT_FOUND);
    }
}
