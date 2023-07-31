package com.example.demo.Jwt.Exception;

import com.example.demo.Common.Exception.RuntimeExceptionWithHttpStatus;
import org.springframework.http.HttpStatus;

public class RefreshTokenExpiredException extends RuntimeExceptionWithHttpStatus {
    public RefreshTokenExpiredException() {
        super("refreshToken이 만료되었습니다.", HttpStatus.UNAUTHORIZED);
    }
}
