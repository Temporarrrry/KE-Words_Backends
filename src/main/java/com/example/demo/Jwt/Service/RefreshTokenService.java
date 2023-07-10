package com.example.demo.Jwt.Service;


import com.example.demo.Jwt.Exception.RefreshTokenExpiredException;
import com.example.demo.Jwt.auth.JwtToken;
import com.example.demo.Jwt.DTO.RefreshTokenRequestDTO;

public interface RefreshTokenService {

    void saveOrUpdate(RefreshTokenRequestDTO refreshTokenRequestDTO);

    void deleteByUserEmail(String userEmail);

    String findByUserEmail(String userEmail);

    JwtToken reIssueTokens(RefreshTokenRequestDTO refreshTokenRequestDTO) throws RefreshTokenExpiredException;
}
