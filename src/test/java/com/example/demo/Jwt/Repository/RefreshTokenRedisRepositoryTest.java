package com.example.demo.Jwt.Repository;

import com.example.demo.Jwt.Entity.RefreshToken;
import com.example.demo.Jwt.Exception.RefreshTokenNotExistException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RefreshTokenRedisRepositoryTest {

    @Autowired
    private RefreshTokenRedisRepository refreshTokenRedisRepository;

    @Test
    void findByUserEmail() {
        String userEmail = "admin";
        String refreshTokenValue = "refreshTokenValue";
        Long expiration = 1209600000L;
        refreshTokenRedisRepository.save(new RefreshToken(userEmail, refreshTokenValue, expiration));


        refreshTokenRedisRepository.findById(userEmail).orElseThrow(RefreshTokenNotExistException::new);
    }
}