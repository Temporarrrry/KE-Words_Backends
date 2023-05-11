package com.example.demo.Jwt.Exception;

public class AccessTokenExpiredException extends RuntimeException {
    public AccessTokenExpiredException() {
        super("accessToken이 만료되었습니다.");
    }
}
