package com.example.demo.Quiz.SentenceQuiz.Repository;

import com.example.demo.Quiz.SentenceQuiz.Entity.SentenceQuiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SentenceQuizRepository extends JpaRepository<SentenceQuiz, Long> {

    @Override
    <S extends SentenceQuiz> S save(S entity);

    boolean existsById(Long id);

    Optional<SentenceQuiz> findById(Long id);

    List<SentenceQuiz> findAllByUserIdAndIsCompletedIsFalse(Long userId);

    Page<SentenceQuiz> findAllByUserId(Long userId, Pageable pageable);
}
