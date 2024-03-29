package com.example.demo.Jwt.auth;

import com.example.demo.Jwt.Exception.AccessTokenExpiredException;
import com.example.demo.Jwt.Exception.RefreshTokenExpiredException;
import com.example.demo.Jwt.Exception.RefreshTokenNotExistException;
import com.example.demo.Jwt.Exception.TokenNotValidException;
import com.example.demo.Jwt.Repository.RefreshTokenRedisRepository;
import com.example.demo.Jwt.Service.LogoutAccessTokenService;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    @Value("${jwt.accessToken.tokenPrefix}")
    private String tokenPrefix;

    @Value("${jwt.accessToken.GettingHeaderName}")
    private String accessTokenGettingHeaderName;
    @Value("${jwt.refreshToken.headerName}")
    private String refreshTokenHeaderName;

    @Value("${jwt.accessToken.secret}")
    private String accessTokenSecretKey;

    @Value("${jwt.refreshToken.secret}")
    private String refreshTokenSecretKey;

    @Value("${jwt.accessToken.expirationMs}")
    private long accessTokenValidTime;

    @Value("${jwt.refreshToken.expirationMs}")
    private long refreshTokenValidTime;

    private final LogoutAccessTokenService logoutAccessTokenService;

    private final RefreshTokenRedisRepository refreshTokenRedisRepository;



    @PostConstruct
    protected void init() {
        accessTokenSecretKey = Base64.getEncoder().encodeToString(accessTokenSecretKey.getBytes());
        refreshTokenSecretKey = Base64.getEncoder().encodeToString(refreshTokenSecretKey.getBytes());
    }


    public String createAccessToken(Long userId, String userEmail) {
        Date now = new Date();

        return Jwts.builder()
                //header

                //payload
                .claim("userId", userId)
                .claim("userEmail", userEmail)
                .setIssuedAt(now) // token 생성 날짜
                .setExpiration(new Date(now.getTime() + accessTokenValidTime))
                //signature
                .signWith(SignatureAlgorithm.HS256, accessTokenSecretKey)
                .compact();
    }

    public String createRefreshToken(Long userId, String userEmail) {
        Date now = new Date();

        return Jwts.builder()
                //header

                //payload
                .claim("userId", userId)
                .claim("userEmail", userEmail)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime))
                //signature
                .signWith(SignatureAlgorithm.HS256, refreshTokenSecretKey)
                .compact();
    }


    public String getUserEmailByAccessToken(String accessToken) throws AccessTokenExpiredException {
        return Jwts.parser()
                .setSigningKey(accessTokenSecretKey)
                .parseClaimsJws(accessToken).getBody()
                .get("userEmail", String.class);
    }


    public String getUserEmailByRefreshToken(String refreshToken) throws RefreshTokenExpiredException {
        return Jwts.parser()
                .setSigningKey(refreshTokenSecretKey)
                .parseClaimsJws(refreshToken).getBody()
                .get("userEmail", String.class);
    }

    public Long getUserIdByAccessToken(String accessToken) throws AccessTokenExpiredException {
        return Jwts.parser()
                .setSigningKey(accessTokenSecretKey)
                .parseClaimsJws(accessToken).getBody()
                .get("userId", Long.class);
    }

    public Long getUserIdByRefreshToken(String refreshToken) throws RefreshTokenExpiredException {
        return Jwts.parser()
                .setSigningKey(refreshTokenSecretKey)
                .parseClaimsJws(refreshToken).getBody()
                .get("userId", Long.class);
    }

    public Long getRemainingTimeByAccessToken(String accessToken) {
        long expiration = Jwts.parser()
                .setSigningKey(accessTokenSecretKey)
                .parseClaimsJws(accessToken).getBody()
                .getExpiration().getTime();

        return (expiration - new Date().getTime()) / 1000;
    }

    public Long getRemainingTimeByRefreshToken(String refreshToken) {
        long expiration = Jwts.parser()
                .setSigningKey(refreshTokenSecretKey)
                .parseClaimsJws(refreshToken).getBody()
                .getExpiration().getTime();

        return expiration - new Date().getTime();
    }


    public Optional<String> getAccessTokenByRequest(HttpServletRequest request) {
        String accessToken = request.getHeader(accessTokenGettingHeaderName);
        if (accessToken == null) return Optional.empty();

        return Optional.of(accessToken.replace(tokenPrefix, ""));
    }

    public Optional<String> getRefreshTokenByRequest(HttpServletRequest request) {
        String refreshToken = request.getHeader(refreshTokenHeaderName);
        if (refreshToken == null) return Optional.empty();

        return Optional.of(refreshToken.replace(tokenPrefix, ""));
    }



    public void validateAccessToken(String accessToken) throws AccessTokenExpiredException, TokenNotValidException {
        try{
            Jwts.parser().setSigningKey(accessTokenSecretKey).parseClaimsJws(accessToken); // parsing 시에 검증 됨
            // refreshToken과 반대로 accessToken이 존재하면 거부, 존재하지 않으면 허가
            logoutAccessTokenService.findByAccessToken(accessToken)
                    .ifPresent(logoutAccessToken -> {throw new TokenNotValidException();});
        } catch (ExpiredJwtException e) {
            throw new AccessTokenExpiredException();
        } catch (MalformedJwtException | SignatureException e) {
            throw new TokenNotValidException();
        }
    }

    public void validateRefreshToken(String refreshToken) throws RefreshTokenExpiredException, TokenNotValidException {
        try{
            Jwts.parser().setSigningKey(refreshTokenSecretKey).parseClaimsJws(refreshToken); // parsing 시에 검증 됨
            // accessToken과 반대로 refreshToken이 존재하면 허가, 존재하지 않으면 거부
            if (refreshTokenRedisRepository.findById(getUserEmailByRefreshToken(refreshToken)).isEmpty())
                throw new RefreshTokenNotExistException();
        } catch (ExpiredJwtException e) {
            throw new RefreshTokenExpiredException();
        } catch (MalformedJwtException | SignatureException e) {
            throw new TokenNotValidException();
        }
    }
}
