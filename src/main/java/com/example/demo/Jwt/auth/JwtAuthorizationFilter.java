package com.example.demo.Jwt.auth;

import com.example.demo.Jwt.Exception.AccessTokenExpiredException;
import com.example.demo.Jwt.Exception.AccessTokenNotExistException;
import com.example.demo.Jwt.Exception.RefreshTokenExpiredException;
import com.example.demo.Jwt.Exception.TokenNotValidException;
import com.example.demo.Member.Entity.Member;
import com.example.demo.Member.Entity.PrincipalDetails;
import com.example.demo.Member.Service.MemberService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsProcessor;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;


public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    private final CorsProcessor corsProcessor;

    private final CorsConfigurationSource corsConfigurationSource;

    private final MemberService memberService;

    public JwtAuthorizationFilter(JwtTokenProvider jwtTokenProvider, CorsProcessor corsProcessor, CorsConfigurationSource corsConfigurationSource, MemberService memberService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.corsProcessor = corsProcessor;
        this.corsConfigurationSource = corsConfigurationSource;
        this.memberService = memberService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            System.out.println("authorization 시작");
            System.out.println("url: " + request.getRequestURL());

            //CORS 설정
            CorsConfiguration corsConfiguration = corsConfigurationSource.getCorsConfiguration(request);
            if (corsConfiguration != null) {
                corsProcessor.processRequest(corsConfiguration, request, response);
            }


            //TODO test를 위해서 임시로 사용자 정보 X
            /*String accessToken = jwtTokenProvider.getAccessTokenByRequest(request)
                    .orElseThrow(AccessTokenNotExistException::new);

            // logout된 token일 경우 TokenNotValidException
            // accessToken이 만료된 경우 AccessTokenExpiredException
            // accessToken이 만료되지 않은 경우 계속 진행
            jwtTokenProvider.validateAccessToken(accessToken);*/

            String userEmail = "admin";//jwtTokenProvider.getUserEmailByAccessToken(accessToken)

            Optional<Member> findMember = memberService.findByUserEmail(userEmail);


            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(
                            userEmail,
                            null,
                            findMember // 이 곳에서 role을 등록해야 @PreAuthorize에서 사용 가능
                                    .map(PrincipalDetails::new)
                                    .orElseThrow(TokenNotValidException::new)
                                    .getAuthorities()
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
