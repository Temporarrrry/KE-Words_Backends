package com.example.demo.Jwt.auth;

import com.example.demo.Jwt.Service.RefreshTokenService;
import com.example.demo.Member.Entity.PrincipalDetails;
import com.example.demo.Member.Exception.MemberNotExistException;
import com.example.demo.Member.Service.MemberService;
import com.example.demo.Member.dto.MemberRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;


public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final String accessTokenHeaderName;
    private final String refreshTokenHeaderName;
    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final MemberService memberService;

    public JwtAuthenticationFilter(String accessTokenHeaderName, String refreshTokenHeaderName, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, RefreshTokenService refreshTokenService, MemberService memberService) throws Exception {
        this.accessTokenHeaderName = accessTokenHeaderName;
        this.refreshTokenHeaderName = refreshTokenHeaderName;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.refreshTokenService = refreshTokenService;
        this.memberService = memberService;

        setFilterProcessesUrl("/api/member/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("attempt authentication");
        MemberRequestDTO memberRequestDTO = null;

        try {
            // 이 부분을 위해서 MemberRequestDTO에 NoArgsConStructor가 필요함
            ObjectMapper objectMapper = new ObjectMapper();
            memberRequestDTO = objectMapper.readValue(request.getInputStream(), MemberRequestDTO.class);
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

        refreshTokenService.saveOrUpdate(principalDetails.getUsername(), refreshToken); // 새로 발급한 refreshToken 저장

        Long id = memberService.findIdByUserEmail(principalDetails.getUsername())
                .orElseThrow(MemberNotExistException::new);

        // body에 tokens
        HashMap<String, Object> responseBodyWriting = new HashMap<>();
        responseBodyWriting.put("id", id); // body에 userId 추가
        responseBodyWriting.put(accessTokenHeaderName, accessToken); //body에 accessToken 추가
        responseBodyWriting.put(refreshTokenHeaderName, refreshToken); //body에 refreshToken 추가
        response.setContentType(MediaType.APPLICATION_JSON_VALUE); // body type 설정
        response.getWriter().print(responseBodyWriting); //body에 작성


        // header에 tokens 작성
        //response.addHeader(accessTokenHeaderName, accessToken);
        //response.addHeader(refreshTokenHeaderName, refreshToken);
        response.setStatus(HttpStatus.OK.value());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        System.out.println("authentication fail");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
}
