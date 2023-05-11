package com.example.demo.Jwt.Repository;

import com.example.demo.Jwt.Entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    //CREATE
    @Override
    <S extends RefreshToken> S save(S entity);

    //DELETE
    void deleteByUserEmail(String userEmail);

    //READ
    Optional<RefreshToken> findByUserEmail(String userEmail);

    boolean existsByUserEmail(String userEmail);
}
