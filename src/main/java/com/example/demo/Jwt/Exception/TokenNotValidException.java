package com.example.demo.Jwt.Exception;

public class TokenNotValidException extends RuntimeException {
    public TokenNotValidException() {
        super("유효하지 않은 토큰입니다.");
    }
}
