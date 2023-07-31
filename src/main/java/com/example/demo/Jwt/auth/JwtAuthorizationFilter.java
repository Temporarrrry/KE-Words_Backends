package com.example.demo.Jwt.auth;

import com.example.demo.Jwt.Exception.AccessTokenExpiredException;
import com.example.demo.Jwt.Exception.AccessTokenNotExistException;
import com.example.demo.Jwt.Exception.TokenNotValidException;
import com.example.demo.Member.Entity.Member;
import com.example.demo.Member.Entity.PrincipalDetails;
import com.example.demo.Member.Service.MemberService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    private final RequestMatcher excludeRequestMatcher;

    private final MemberService memberService;

    public JwtAuthorizationFilter(JwtTokenProvider jwtTokenProvider, MemberService memberService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberService = memberService;
        this.excludeRequestMatcher = new OrRequestMatcher(
                new AntPathRequestMatcher("/api/member/register", HttpMethod.POST.name()),
                new AntPathRequestMatcher("/api/member/userEmailDuplicatedCheck", HttpMethod.GET.name()),

                new AntPathRequestMatcher("/api/token/reIssue", HttpMethod.POST.name()),

                new AntPathRequestMatcher("/", HttpMethod.GET.name()),

                new AntPathRequestMatcher("/swagger-ui/**", HttpMethod.GET.name()),
                new AntPathRequestMatcher("/v3/api-docs/**", HttpMethod.GET.name()),

                new AntPathRequestMatcher("/assets/**", HttpMethod.GET.name())
        );
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            log.info("authorization 시작");
            log.info("url: " + request.getRequestURL());

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

        } catch (AccessTokenExpiredException e){
            log.debug("AccessToken Expired: ", e);
            response.sendError(e.getHttpStatus().value(), e.getMessage());
        } catch (AccessTokenNotExistException e) {
            log.debug("Token Not Exist: ", e);
            response.sendError(e.getHttpStatus().value(), e.getMessage());
        } catch (TokenNotValidException e) { //
            log.warn("Token Not Valid: ", e);
            response.sendError(e.getHttpStatus().value(), e.getMessage());
        } catch (Exception e) {
            log.error("unhandled error: ", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "처리되지 않은 에러입니다.");
        }
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return excludeRequestMatcher.matches(request);
    }
}
