package com.example.demo.Sentence.AddOn.LastSentence.Exception;

import com.example.demo.Common.Exception.RuntimeExceptionWithHttpStatus;
import org.springframework.http.HttpStatus;

public class LastSentenceNotExistException extends RuntimeExceptionWithHttpStatus {
    public LastSentenceNotExistException() {
        super("마지막으로 본 문장이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
    }
}
