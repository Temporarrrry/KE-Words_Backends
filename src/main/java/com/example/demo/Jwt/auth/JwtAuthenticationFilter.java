package com.example.demo.Jwt.auth;

import com.example.demo.Jwt.Service.JwtTokenService;
import com.example.demo.Member.Entity.PrincipalDetails;
import com.example.demo.Member.dto.MemberRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;


public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final String accessTokenHeaderName;
    private final String refreshTokenHeaderName;
    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtTokenService jwtTokenService;

    public JwtAuthenticationFilter(String accessTokenHeaderName, String refreshTokenHeaderName, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, JwtTokenService jwtTokenService) throws Exception {
        this.accessTokenHeaderName = accessTokenHeaderName;
        this.refreshTokenHeaderName = refreshTokenHeaderName;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtTokenService = jwtTokenService;

        setFilterProcessesUrl("/member/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("attempt authentication");
        ObjectMapper mapper = new ObjectMapper();
        MemberRequestDTO memberRequestDTO = null;

        try {
            // 이 부분을 위해서 MemberRequestDTO에 NoArgsConStructor가 필요함
            memberRequestDTO = mapper.readValue(request.getInputStream(), MemberRequestDTO.class);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assert memberRequestDTO != null;

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                memberRequestDTO.getUserEmail(), memberRequestDTO.getPassword());

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain filterChain, Authentication authResult) throws IOException, ServletException {
        System.out.println("authentication success");

        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        String accessToken = jwtTokenProvider.createAccessToken(principalDetails.getUsername());
        String refreshToken = jwtTokenProvider.createRefreshToken(principalDetails.getUsername());


        jwtTokenService.saveOrUpdate(principalDetails.getUsername(), refreshToken);

        response.addHeader(accessTokenHeaderName, accessToken);
        response.addHeader(refreshTokenHeaderName, refreshToken);
        response.setStatus(HttpStatus.OK.value());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        System.out.println("authentication fail");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
}
