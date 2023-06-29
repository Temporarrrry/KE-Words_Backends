package com.example.demo.Jwt.dto;

import com.example.demo.Jwt.Entity.LogoutAccessToken;
import lombok.Data;

@Data
public class LogoutAccessTokenResponseDTO {

    private String accessToken;

    private Long expiration;

    public LogoutAccessTokenResponseDTO(LogoutAccessToken logoutAccessToken) {
        this.accessToken = logoutAccessToken.getAccessToken();
        this.expiration = logoutAccessToken.getExpiration();
    }
}
