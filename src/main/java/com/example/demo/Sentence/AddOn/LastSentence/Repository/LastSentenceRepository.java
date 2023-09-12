package com.example.demo.Sentence.AddOn.LastSentence.Repository;

import com.example.demo.Sentence.AddOn.LastSentence.Entity.LastSentence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LastSentenceRepository extends JpaRepository<LastSentence, Long> {

    @Override
    <S extends LastSentence> S save(S entity);

    Optional<LastSentence> findByUserId(Long userId);

    void deleteByUserId(Long userId);

    Boolean existsByUserId(Long userId);
}
