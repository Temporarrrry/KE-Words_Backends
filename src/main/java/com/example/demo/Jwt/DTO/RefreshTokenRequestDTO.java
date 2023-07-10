package com.example.demo.Jwt.DTO;

import com.example.demo.Jwt.Entity.RefreshToken;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RefreshTokenRequestDTO {

    private String userEmail;

    @NotBlank
    private String refreshToken;

    private Long expiration;

    public RefreshToken toEntity() {
        return RefreshToken.builder()
                .userEmail(userEmail)
                .refreshToken(refreshToken)
                .expiration(expiration).build();
    }
}
