package com.example.demo.Jwt.Entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(value = "RefreshToken", timeToLive = 1209600000) // TTL을 수정하면 yml도 수정해야함
public class RefreshToken {
    @Id // @Id annotation의 패키지에 주의
    private String userEmail;

    private String refreshToken;

    @TimeToLive
    private Long expiration;
}
