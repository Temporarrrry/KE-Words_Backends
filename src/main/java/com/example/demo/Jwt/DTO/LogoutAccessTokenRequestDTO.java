package com.example.demo.Jwt.DTO;

import com.example.demo.Jwt.Entity.LogoutAccessToken;
import lombok.Data;

@Data
public class LogoutAccessTokenRequestDTO {

    private String accessToken;

    private Long remainingTime;

    public LogoutAccessTokenRequestDTO(String accessToken, Long remainingTime) {
        this.accessToken = accessToken;
        this.remainingTime = remainingTime;
    }

    public LogoutAccessToken toEntity() {
        return LogoutAccessToken.builder()
                .accessToken(accessToken)
                .expiration(remainingTime)
                .build();
    }
}
