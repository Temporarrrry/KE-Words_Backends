package com.example.demo.Common.Config;

import com.example.demo.Jwt.Service.RefreshTokenService;
import com.example.demo.Jwt.auth.JwtAuthenticationFilter;
import com.example.demo.Jwt.auth.JwtAuthorizationFilter;
import com.example.demo.Jwt.auth.JwtTokenProvider;
import com.example.demo.Member.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Value("${jwt.accessToken.SendingHeaderName}")
    private String accessTokenSendingHeaderName;
    @Value("${jwt.refreshToken.headerName}")
    private String refreshTokenHeaderName;

    @Value("${spring.origin}")
    private String origin;

    private final JwtTokenProvider jwtTokenProvider;

    private final RefreshTokenService refreshTokenService;

    private final MemberService memberService;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> {
            web.ignoring().requestMatchers(
                    "/api/member/register", "/api/member/userEmailDuplicatedCheck", // register,emailcheck 시에 security 미적용
                    "/api/token/reIssue",
                    "/swagger-ui/**", "/v3/api-docs/**", // swagger-ui 보안 미적용
                    "/assets/**"
            );
        };
    }


    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager,
                                              CorsConfigurationSource corsConfigurationSource) throws Exception{

        http
                .cors()// cors 적용
                    .and()
                .csrf().disable() // csrf 비활성화
                .headers().frameOptions().disable()
                        .and()

                .formLogin().disable() // 폼 로그인 비활성화
                .httpBasic().disable() //httpBasic 비활성화

                // Rest api를 위해 server를 stateless하게 유지
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                // 로그아웃 설정
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/member/login") // 로그아웃 시 이동할 페이지 지정
                    .invalidateHttpSession(true) // 로그아웃 시 세션 제거

                    .and()
                // 에러 설정
                .exceptionHandling().accessDeniedPage("/denied"); // 에러 페이지 설정

        //JWT filter 설정
        http
                //AuthenticationFilter
                .addFilterBefore(
                        new JwtAuthenticationFilter(
                                accessTokenSendingHeaderName,
                                refreshTokenHeaderName,
                                authenticationManager,
                                jwtTokenProvider,
                                refreshTokenService,
                                memberService
                        ),
                        UsernamePasswordAuthenticationFilter.class)
                //AuthorizationFilter
                .addFilterAfter(
                        new JwtAuthorizationFilter(
                                origin,
                                jwtTokenProvider,
                                corsConfigurationSource
                        ),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public RoleHierarchyImpl roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_MEMBER");
        return roleHierarchy;
    }

    @Bean
    public DefaultWebSecurityExpressionHandler expressionHandler() {
        DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy());
        return expressionHandler;
    }
}
