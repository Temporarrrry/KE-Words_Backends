package com.example.demo.Jwt.Service;


import com.example.demo.Jwt.auth.JwtToken;

public interface RefreshTokenService {

    void saveOrUpdate(String userEmail, String refreshToken);

    void deleteByEmail(String userEmail);

    String findByEmail(String userEmail);

    JwtToken reIssueTokens(String refreshToken) throws Exception;
}
