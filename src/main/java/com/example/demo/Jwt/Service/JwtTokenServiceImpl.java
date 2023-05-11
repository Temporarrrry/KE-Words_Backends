package com.example.demo.Jwt.Service;

import com.example.demo.Jwt.Entity.RefreshToken;
import com.example.demo.Jwt.Exception.RefreshTokenNotExistException;
import com.example.demo.Jwt.Repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    @Override
    public void saveOrUpdate(String userEmail, String refreshToken) {
        RefreshToken refreshToken1 = RefreshToken.builder()
                .userEmail(userEmail)
                .refreshToken(refreshToken).build();
        refreshTokenRepository.save(refreshToken1);
    }

    @Override
    public void deleteByEmail(String userEmail) {
        refreshTokenRepository.deleteByUserEmail(userEmail);
    }

    @Override
    public String findByEmail(String userEmail) {
        return refreshTokenRepository.findByUserEmail(userEmail).orElseThrow(RefreshTokenNotExistException::new).getRefreshToken();
    }
}
