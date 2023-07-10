package com.example.demo.Jwt.Service;


import com.example.demo.Jwt.Entity.LogoutAccessToken;
import com.example.demo.Jwt.DTO.LogoutAccessTokenRequestDTO;

import java.util.Optional;

public interface LogoutAccessTokenService {

    void saveLogoutAccessToken(LogoutAccessTokenRequestDTO logoutAccessTokenRequestDTO);

    Optional<LogoutAccessToken> findByAccessToken(String accessToken);
}
