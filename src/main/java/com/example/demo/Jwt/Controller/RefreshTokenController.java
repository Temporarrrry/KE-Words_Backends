package com.example.demo.Jwt.Controller;

import com.example.demo.Jwt.Exception.RefreshTokenExpiredException;
import com.example.demo.Jwt.DTO.RefreshTokenRequestDTO;
import com.example.demo.Jwt.Service.RefreshTokenService;
import com.example.demo.Jwt.auth.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/token")
@RequiredArgsConstructor
public class RefreshTokenController {
    private final RefreshTokenService refreshTokenService;

    @RequestMapping(method = RequestMethod.POST, value = "/reIssue")
    public ResponseEntity<JwtToken> reIssueAccessToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO) throws RefreshTokenExpiredException {
        JwtToken jwtToken = refreshTokenService.reIssueTokens(refreshTokenRequestDTO);
        return new ResponseEntity<>(jwtToken, HttpStatus.OK);
    }
}
