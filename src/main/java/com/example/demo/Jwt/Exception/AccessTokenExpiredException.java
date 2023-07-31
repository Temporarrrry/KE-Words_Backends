package com.example.demo.Jwt.Exception;

import com.example.demo.Common.Exception.RuntimeExceptionWithHttpStatus;
import org.springframework.http.HttpStatus;

public class AccessTokenExpiredException extends RuntimeExceptionWithHttpStatus {
    public AccessTokenExpiredException() {
        super("accessToken이 만료되었습니다.", HttpStatus.UNAUTHORIZED);
    }
}
