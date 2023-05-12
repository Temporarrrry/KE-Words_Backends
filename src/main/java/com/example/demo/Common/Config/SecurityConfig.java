package com.example.demo.Common.Config;

import com.example.demo.Jwt.Service.JwtTokenService;
import com.example.demo.Jwt.auth.JwtAuthenticationFilter;
import com.example.demo.Jwt.auth.JwtAuthorizationFilter;
import com.example.demo.Jwt.auth.JwtTokenProvider;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Value("${jwt.accessToken.headerName}")
    private String accessTokenHeaderName;
    @Value("${jwt.refreshToken.headerName}")
    private String refreshTokenHeaderName;

    private final JwtTokenProvider jwtTokenProvider;

    private final JwtTokenService jwtTokenService;



    @Bean
    public PasswordEncoder passwordEncoder(){ // bean이면 spring security에서 자동으로 적용해줌
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> {
            web.ignoring().requestMatchers(
                    "/api/member/register", "/api/member/userEmailDuplicatedCheck", // register 시에 security 미적용
                    "/swagger-ui/**", "/v3/api-docs/**", // swagger-ui 보안 미적용
                    "/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/assets/**",
                    "/resources/static/assets/**", "/index.html", "/home", "/temporal/**" // 임시
            );
        };
    }


    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception{

        http
                .csrf().disable() // csrf 비활성화
                .headers().frameOptions().disable()
                        .and()
                .formLogin().disable() // 폼 로그인 비활성화
                .httpBasic().disable() //httpBasic 비활성화

                .authorizeHttpRequests()
                .requestMatchers("/api/**").authenticated()
                .anyRequest().permitAll()

                    .and()



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
                                accessTokenHeaderName,
                                refreshTokenHeaderName,
                                authenticationManager,
                                jwtTokenProvider,
                                jwtTokenService),
                        UsernamePasswordAuthenticationFilter.class)
                //AuthorizationFilter
                .addFilterAfter(new JwtAuthorizationFilter(
                                accessTokenHeaderName,
                                refreshTokenHeaderName,
                                jwtTokenProvider,
                                jwtTokenService),
                        UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    /*@Bean
    public FilterRegistrationBean<JwtAuthenticationFilter> jwtAuthenticationFilterFilterRegistration(
            AuthenticationManager authenticationManager
    ) throws Exception {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(
                new JwtAuthenticationFilter(
                        accessTokenHeaderName,
                        refreshTokenHeaderName,
                        authenticationManager,
                        jwtTokenProvider,
                        jwtTokenService)
        );
        registrationBean.addUrlPatterns("/api/member/login");
        registrationBean.setOrder(1);
        registrationBean.setName("first");
        return registrationBean;
    }*/



    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOriginPatterns(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("HEAD","POST","GET","DELETE","PUT", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowCredentials(true);
        
        /*config.setAllowCredentials(true);
        config.setAllowedOrigins(Collections.singletonList("*"));
        config.setAllowedHeaders(Collections.singletonList("*"));
        config.setAllowedMethods(Collections.singletonList("*"));*/

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", config);
        source.registerCorsConfiguration("/login/proc", config);

        return source;
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
