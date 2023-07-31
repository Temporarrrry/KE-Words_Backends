package com.example.demo.Jwt.Exception;

import com.example.demo.Common.Exception.RuntimeExceptionWithHttpStatus;
import org.springframework.http.HttpStatus;

public class RefreshTokenNotExistException extends RuntimeExceptionWithHttpStatus {
    public RefreshTokenNotExistException() {
        super("refreshToken이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
    }
}
