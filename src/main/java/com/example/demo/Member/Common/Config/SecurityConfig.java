package com.example.demo.Member.Common.Config;

import com.example.demo.Member.Handler.LoginFailureHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){ // bean이면 spring security에서 자동으로 적용해줌
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        //TODO 개발완료 후 csrf disable 설정해야함
        http.csrf().disable().headers().frameOptions().disable();

        /*// URL 접근 권한 블랙리스트 방식 설정(일반: 허용 / 지정 경로: 차단)
        http.authorizeHttpRequests(authorize -> authorize
                .shouldFilterAllDispatcherTypes(false).anyRequest().permitAll() // 그 외의 페이지는 전부 허용
        );*/

        // 로그인 설정
        http.formLogin()
                .loginPage("/member/login") // 로그인하는 페이지를 지정할 때 사용
                .usernameParameter("userId") // username을 userId로 지정
                .passwordParameter("passwd") // password를 passwd로 지정
                .loginProcessingUrl("/loginProc") //loginProc으로 들어오면 UserDetailsService 구현한 loadUser에 자동으로 들어감
                .defaultSuccessUrl("/") // 로그인에 성공했을 때 기본적으로 이동하는 페이지를 지정할 때 사용
                //.successHandler(new LoginSuccessHandler()) //일단 굳이 필요없어 보임
                .failureHandler(new LoginFailureHandler());
                //.failureUrl("/login"); //로그인에 실패 했을 때 이동하는 페이지, 일단 기본 로그인 페이지로 적용


        // 로그아웃 설정
        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login") // 로그아웃 시 이동할 페이지 지정
                .invalidateHttpSession(true); // 로그아웃 시 세션 없앨 것인가

        //세션 설정
        http.sessionManagement(session -> session
                .invalidSessionUrl("/invalid") // 유효하지 않은 세션의 경우 이동하는 페이지
                .maximumSessions(10) // 세션 최대 연결 수
                .maxSessionsPreventsLogin(true) // 세션 최대 연결 수에서 세션을 더 생성하려고 하면 막음(true)
                .expiredUrl("/") // 세션 만료 시 이동하는 페이지

        );

        // 에러 설정
        http.exceptionHandling().accessDeniedPage("/denied"); // 에러 페이지 설정



        return http.build();
    }

}
