package com.example.demo.Jwt.Exception;

import com.example.demo.Common.Exception.RuntimeExceptionWithHttpStatus;
import org.springframework.http.HttpStatus;

public class AccessTokenNotExistException extends RuntimeExceptionWithHttpStatus {
    public AccessTokenNotExistException() {
        super("accessToken이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
    }
}
