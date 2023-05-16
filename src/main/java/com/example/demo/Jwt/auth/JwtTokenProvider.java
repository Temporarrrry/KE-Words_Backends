package com.example.demo.Jwt.auth;

import com.example.demo.Jwt.Exception.AccessTokenExpiredException;
import com.example.demo.Jwt.Exception.AccessTokenNotExistException;
import com.example.demo.Jwt.Exception.RefreshTokenExpiredException;
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



    @PostConstruct
    protected void init() {
        accessTokenSecretKey = Base64.getEncoder().encodeToString(accessTokenSecretKey.getBytes());
        refreshTokenSecretKey = Base64.getEncoder().encodeToString(refreshTokenSecretKey.getBytes());
    }


    public String createAccessToken(String memberPk) {
        Claims claims = Jwts.claims().setSubject(memberPk);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, accessTokenSecretKey)
                .compact();
    }

    public String createRefreshToken(String memberPk) {
        Claims claims = Jwts.claims().setSubject(memberPk);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, refreshTokenSecretKey)
                .compact();
    }


    public String getUserEmailByAccessTokenRequest(HttpServletRequest request) throws Exception {
        return getUserEmailByAccessToken(
                getAccessTokenByRequest(request)
                        .orElseThrow(AccessTokenNotExistException::new)
        );
    }


    public String getUserEmailByAccessToken(String accessToken) throws Exception {
        validateAccessToken(accessToken);
        return Jwts.parser().setSigningKey(accessTokenSecretKey).parseClaimsJws(accessToken).getBody().getSubject();
    }


    public String getUserEmailByRefreshToken(String token) {
        return Jwts.parser().setSigningKey(refreshTokenSecretKey).parseClaimsJws(token).getBody().getSubject();
    }


    /*public Authentication getAuthentication(PrincipalDetails principalDetails) {
        return new UsernamePasswordAuthenticationToken(principalDetails, "", principalDetails.getAuthorities());
    }*/

    public Optional<String> getAccessTokenByRequest(HttpServletRequest request) {
        String accessToken = request.getHeader(accessTokenGettingHeaderName);
        if (accessToken == null) return Optional.empty();

        return Optional.of(accessToken.replace(tokenPrefix, ""));
    }

    public Optional<String> getRefreshTokenByRequest(HttpServletRequest request) {
        String refreshToken = request.getHeader(refreshTokenHeaderName);
        if (refreshToken == null) return Optional.empty();

        return Optional.of(refreshToken);
    }



    public void validateAccessToken(String accessToken) throws AccessTokenExpiredException {
        try{
            Jws<Claims> claims = Jwts.parser().setSigningKey(accessTokenSecretKey).parseClaimsJws(accessToken);
        } catch (ExpiredJwtException e) {
            System.out.println("access Ex");
            throw new AccessTokenExpiredException();
        }
    }

    public void validateRefreshToken(String refreshToken) throws RefreshTokenExpiredException {
        try{
            Jws<Claims> claims = Jwts.parser().setSigningKey(refreshTokenSecretKey).parseClaimsJws(refreshToken);
        } catch (ExpiredJwtException e) {
            throw new RefreshTokenExpiredException();
        }
    }

    /*public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }*/
}
