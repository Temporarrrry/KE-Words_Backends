package com.example.demo.Jwt.auth;

import com.example.demo.Jwt.Exception.AccessTokenExpiredException;
import com.example.demo.Jwt.Exception.AccessTokenNotExistException;
import com.example.demo.Jwt.Exception.RefreshTokenExpiredException;
import com.example.demo.Jwt.Exception.TokenNotValidException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsProcessor;
import org.springframework.web.cors.DefaultCorsProcessor;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;


public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final String origin;
    private final JwtTokenProvider jwtTokenProvider;

    private final CorsConfigurationSource corsConfigurationSource;

    public JwtAuthorizationFilter(String origin, JwtTokenProvider jwtTokenProvider, CorsConfigurationSource corsConfigurationSource) {
        this.origin = origin;
        this.jwtTokenProvider = jwtTokenProvider;
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

            // accessToken이 존재하는 경우
            if (accessTokenByRequest.isPresent()) {
                // accessToken이 만료된 경우 AccessTokenExpiredException throw
                jwtTokenProvider.validateAccessToken(accessTokenByRequest.get());
                // accessToken이 만료되지 않은 경우 계속 진행
            }
            // accessToken이 존재하지 않는 경우 throw exception
            else throw new AccessTokenNotExistException();

            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(
                        jwtTokenProvider.getUserEmailByAccessTokenRequest(request), null
                    )
            );

            chain.doFilter(request, response); //filter 계속 진행

        } catch (AccessTokenExpiredException | RefreshTokenExpiredException e){
            System.out.println("TokenExpired: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        } catch (TokenNotValidException | AccessTokenNotExistException e) { //
            System.out.println("token not valid: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (Exception e) {
            System.out.println("unhandled error: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "처리되지 않은 에러입니다.");
        }
    }


    /*@Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String requestURL = request.getRequestURL().toString();
        System.out.println("author requestURL: " + requestURL);
        return !requestURL.startsWith(origin + "/api/");
    }*/
}
