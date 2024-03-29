package com.example.demo.Jwt.Service;

import com.example.demo.Jwt.DTO.RefreshTokenRequestDTO;
import com.example.demo.Jwt.Entity.RefreshToken;
import com.example.demo.Jwt.Exception.RefreshTokenNotExistException;
import com.example.demo.Jwt.Repository.RefreshTokenRedisRepository;
import com.example.demo.Jwt.auth.JwtToken;
import com.example.demo.Jwt.auth.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRedisRepository refreshTokenRedisRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void saveOrUpdate(RefreshTokenRequestDTO refreshTokenRequestDTO) {
        String refreshToken = refreshTokenRequestDTO.getRefreshToken();

        refreshTokenRedisRepository.save(RefreshToken.builder()
                .userEmail(jwtTokenProvider.getUserEmailByRefreshToken(refreshToken))
                .refreshToken(refreshToken)
                .expiration(jwtTokenProvider.getRemainingTimeByRefreshToken(refreshToken)).build());
    }

    @Override
    public void deleteByUserEmail(String userEmail) {
        refreshTokenRedisRepository.delete(RefreshToken.builder().userEmail(userEmail).build());
    }

    @Override
    public String findByUserEmail(String userEmail) {
        return refreshTokenRedisRepository.findById(userEmail)
                .orElseThrow(RefreshTokenNotExistException::new).getRefreshToken();
    }

    @Override
    public JwtToken reIssueTokens(RefreshTokenRequestDTO refreshTokenRequestDTO) throws RefreshTokenNotExistException {
        String refreshToken = refreshTokenRequestDTO.getRefreshToken();

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
