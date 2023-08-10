package com.example.demo.Common.Exception;

import org.springframework.http.HttpStatus;

public class NoAuthorityException extends RuntimeExceptionWithHttpStatus {
    public NoAuthorityException() {
        super("권한이 없습니다.", HttpStatus.UNAUTHORIZED);
    }
}
