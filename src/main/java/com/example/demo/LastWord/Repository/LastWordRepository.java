package com.example.demo.LastWord.Repository;

import com.example.demo.LastWord.Entity.LastWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LastWordRepository extends JpaRepository<LastWord, Long> {

    @Override
    <S extends LastWord> S save(S entity);

    Optional<LastWord> findByUserId(Long userId);

    void deleteByUserId(Long userId);

    boolean existsByUserId(Long userId);
}
