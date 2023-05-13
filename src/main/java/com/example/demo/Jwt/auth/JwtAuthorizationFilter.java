package com.example.demo.Jwt.auth;

import com.example.demo.Jwt.Exception.AccessTokenExpiredException;
import com.example.demo.Jwt.Exception.RefreshTokenExpiredException;
import com.example.demo.Jwt.Exception.RefreshTokenNotExistException;
import com.example.demo.Jwt.Exception.TokenNotValidException;
import com.example.demo.Jwt.Service.JwtTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsProcessor;
import org.springframework.web.cors.DefaultCorsProcessor;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;


public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final String accessTokenHeaderName;
    private final String refreshTokenHeaderName;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtTokenService jwtTokenService;
    private final CorsConfigurationSource corsConfigurationSource;

    public JwtAuthorizationFilter(String accessTokenHeaderName, String refreshTokenHeaderName, JwtTokenProvider jwtTokenProvider, JwtTokenService jwtTokenService, CorsConfigurationSource corsConfigurationSource) {
        this.accessTokenHeaderName = accessTokenHeaderName;
        this.refreshTokenHeaderName = refreshTokenHeaderName;
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtTokenService = jwtTokenService;
        this.corsConfigurationSource = corsConfigurationSource;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            System.out.println("authorization 시작");
            System.out.println("url: " + request.getRequestURL());

            //CORS 설정
            CorsConfiguration corsConfiguration = corsConfigurationSource.getCorsConfiguration(request);
            if (corsConfiguration != null) {
                CorsProcessor corsProcessor = new DefaultCorsProcessor();
                corsProcessor.processRequest(corsConfiguration, request, response);
            }


            Optional<String> accessTokenByRequest = jwtTokenProvider.getAccessTokenByRequest(request);

            if (accessTokenByRequest.isPresent()) { // accessToken이 존재하는 경우
                // accessToken이 만료된 경우 AccessTokenExpiredException throw
                jwtTokenProvider.validateAccessToken(accessTokenByRequest.get());
                // accessToken이 만료되지 않은 경우 계속 진행
            }
            else { // accessToken이 존재하지 않는 경우
                String refreshToken = jwtTokenProvider.getRefreshTokenByRequest(request)
                        .orElseThrow(RefreshTokenNotExistException::new); // refreshToken이 존재하지 않으면 Exception

                // refreshToken이 만료된 경우 RefreshTokenExpiredException throw
                if(!jwtTokenProvider.validateRefreshToken(refreshToken))
                    throw new TokenNotValidException(); //refreshToken이 유효하지 않은 경우

                // refreshToken이 만료되지 않은 경우
                String userEmail = jwtTokenProvider.getUserEmailByRefreshToken(refreshToken);
                String oldRefreshToken = jwtTokenService.findByEmail(userEmail);
                if (!refreshToken.equals(oldRefreshToken)) throw new TokenNotValidException();

                String newAccessToken = jwtTokenProvider.createAccessToken(userEmail);
                String newRefreshToken = jwtTokenProvider.createRefreshToken(userEmail);

                jwtTokenService.saveOrUpdate(userEmail, newRefreshToken); // refreshToken 저장, 갱신

                response.addHeader(accessTokenHeaderName, newAccessToken); // 새 accessToken 전달
                response.addHeader(refreshTokenHeaderName, newRefreshToken); // 새 refreshToken 전달
            }

            chain.doFilter(request, response); //filter 계속 진행

        } catch (AccessTokenExpiredException | RefreshTokenExpiredException e){
            System.out.println("TokenExpired: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        } catch (TokenNotValidException e) { //
            System.out.println("token not valid: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (RefreshTokenNotExistException e) {
            System.out.println(e.getCause() + e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage()); //프론트에게 refreshToken 필요하다고 전달
        } catch (Exception e) {
            System.out.println("unhandled error: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "처리되지 않은 에러입니다.");
        }
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String requestURL = request.getRequestURL().toString();
        System.out.println("author requestURL: " + requestURL);
        return !requestURL.startsWith("/api/") || !requestURL.startsWith("/assets/");
    }
}
