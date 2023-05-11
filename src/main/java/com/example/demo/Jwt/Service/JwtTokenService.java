package com.example.demo.Jwt.Service;


public interface JwtTokenService {

    void saveOrUpdate(String userEmail, String refreshToken);

    void deleteByEmail(String userEmail);

    String findByEmail(String userEmail);


}
