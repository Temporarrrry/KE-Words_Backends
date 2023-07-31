package com.example.demo.Common.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

@Configuration
public class CorsConfig {
    @Value("${Cors.AllowedOriginPatterns}")
    private String AllowedOriginPatterns;
    @Value("${Cors.AllowedHeaders}")
    private String AllowedHeaders;
    @Value("${Cors.AllowedMethods}")
    private String AllowedMethods;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(Collections.singletonList(AllowedOriginPatterns));
        config.setAllowedHeaders(Collections.singletonList(AllowedHeaders));
        config.setAllowedMethods(Collections.singletonList(AllowedMethods));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
