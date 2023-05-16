package com.example.demo.Jwt.Service;


import com.example.demo.Jwt.Exception.RefreshTokenExpiredException;

public interface RefreshTokenService {

    void saveOrUpdate(String userEmail, String refreshToken);

    void deleteByEmail(String userEmail);

    String findByEmail(String userEmail);

    String reIssueAccessToken(String refreshToken) throws Exception;
}
