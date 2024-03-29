package com.example.demo.Jwt.Service;

import com.example.demo.Jwt.DTO.LogoutAccessTokenRequestDTO;
import com.example.demo.Jwt.Entity.LogoutAccessToken;
import com.example.demo.Jwt.Repository.LogoutRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LogoutAccessTokenServiceImpl implements LogoutAccessTokenService {

    private final LogoutRedisRepository logoutRedisRepository;

    @Override
    public void saveLogoutAccessToken(LogoutAccessTokenRequestDTO logoutAccessTokenRequestDTO) {
        logoutRedisRepository.save(logoutAccessTokenRequestDTO.toEntity());
    }

    @Override
    public Optional<LogoutAccessToken> findByAccessToken(String accessToken) {
        return logoutRedisRepository.findByAccessToken(accessToken);
    }
}
