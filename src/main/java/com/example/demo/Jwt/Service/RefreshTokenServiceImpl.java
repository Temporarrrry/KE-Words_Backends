package com.example.demo.Jwt.Service;

import com.example.demo.Jwt.Entity.RefreshToken;
import com.example.demo.Jwt.Exception.RefreshTokenNotExistException;
import com.example.demo.Jwt.Repository.RefreshTokenRepository;
import com.example.demo.Jwt.auth.JwtTokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

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

    public String reIssueAccessToken(String refreshToken) throws Exception {
        jwtTokenProvider.validateRefreshToken(refreshToken);

        String userEmail = jwtTokenProvider.getUserEmailByRefreshToken(refreshToken);
        return jwtTokenProvider.createAccessToken(userEmail);
    }
}
