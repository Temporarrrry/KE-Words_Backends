package com.example.demo.Jwt.Repository;

import com.example.demo.Jwt.Entity.LogoutAccessToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LogoutRedisRepository extends CrudRepository<LogoutAccessToken, String> {
    @Override
    <S extends LogoutAccessToken> S save(S entity);


    Optional<LogoutAccessToken> findByAccessToken(String accessToken);
}
