package com.example.demo.Common.Config;

import com.example.demo.Jwt.Service.RefreshTokenService;
import com.example.demo.Jwt.auth.JwtAuthenticationFilter;
import com.example.demo.Jwt.auth.JwtAuthorizationFilter;
import com.example.demo.Jwt.auth.JwtTokenProvider;
import com.example.demo.Member.Service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    @Value("${jwt.accessToken.SendingHeaderName}")
    private String accessTokenSendingHeaderName;
    @Value("${jwt.refreshToken.headerName}")
    private String refreshTokenHeaderName;

    @Value("${spring.origin}")
    private String origin;

    private final ObjectMapper objectMapper;

    private final CorsConfigurationSource corsConfigurationSource;

    private final JwtTokenProvider jwtTokenProvider;

    private final RefreshTokenService refreshTokenService;

    private final MemberService memberService;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception{

        http
                .cors(httpSecurityCorsConfigurer ->
                        httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource)
                )

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
                                objectMapper,
                                jwtTokenProvider,
                                refreshTokenService,
                                memberService
                        ),
                        UsernamePasswordAuthenticationFilter.class)
                //AuthorizationFilter
                .addFilterAfter(
                        new JwtAuthorizationFilter(
                                jwtTokenProvider,
                                memberService
                        ),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_MEMBER");
        return roleHierarchy;
    }

    @Bean
    public MethodSecurityExpressionHandler methodSecurityExpressionHandler(RoleHierarchy roleHierarchy) {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy);
        return expressionHandler;
    }
}
