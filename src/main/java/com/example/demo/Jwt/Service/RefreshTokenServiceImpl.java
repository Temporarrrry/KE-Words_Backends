package com.example.demo.Jwt.Service;

import com.example.demo.Jwt.Entity.RefreshToken;
import com.example.demo.Jwt.Exception.RefreshTokenNotExistException;
import com.example.demo.Jwt.Repository.RefreshTokenRedisRepository;
import com.example.demo.Jwt.auth.JwtToken;
import com.example.demo.Jwt.auth.JwtTokenProvider;
import com.example.demo.Jwt.dto.RefreshTokenRequestDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRedisRepository refreshTokenRedisRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void saveOrUpdate(RefreshTokenRequestDTO refreshTokenRequestDTO) {
        refreshTokenRedisRepository.save(RefreshToken.builder()
                .userEmail(refreshTokenRequestDTO.getUserEmail())
                .refreshToken(refreshTokenRequestDTO.getRefreshToken())
                .expiration(refreshTokenRequestDTO.getExpiration()).build());
    }

    @Override
    public void deleteByUserEmail(String userEmail) {
        refreshTokenRedisRepository.delete(RefreshToken.builder().userEmail(userEmail).build());
    }

    @Override
    public String findByUserEmail(String userEmail) {
        return refreshTokenRedisRepository.findByUserEmail(userEmail)
                .orElseThrow(RefreshTokenNotExistException::new).getRefreshToken();
    }

    public JwtToken reIssueTokens(String refreshToken) throws RefreshTokenNotExistException {
        jwtTokenProvider.validateRefreshToken(refreshToken);

        Long userId = jwtTokenProvider.getUserIdByRefreshToken(refreshToken);
        String userEmail = jwtTokenProvider.getUserEmailByRefreshToken(refreshToken);
        String newRefreshToken = jwtTokenProvider.createRefreshToken(userId, userEmail);

        refreshTokenRedisRepository.save(
                RefreshToken.builder()
                        .userEmail(userEmail)
                        .refreshToken(newRefreshToken)
                        .expiration(jwtTokenProvider.getRemainingTimeByRefreshToken(newRefreshToken))
                        .build()
        );

        return new JwtToken(jwtTokenProvider.createAccessToken(userId, userEmail), newRefreshToken);
    }
}
