package com.example.demo.Jwt.Exception;

public class RefreshTokenExpiredException extends RuntimeException {
    public RefreshTokenExpiredException() {
        super("refreshToken이 만료되었습니다.");
    }
}
