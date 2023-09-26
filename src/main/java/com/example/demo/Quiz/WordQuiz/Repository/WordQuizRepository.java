package com.example.demo.Quiz.WordQuiz.Repository;

import com.example.demo.Quiz.WordQuiz.Entity.WordQuiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WordQuizRepository extends JpaRepository<WordQuiz, Long> {

    @Override
    <S extends WordQuiz> S save(S entity);

    boolean existsById(Long id);

    Optional<WordQuiz> findById(Long id);

    List<WordQuiz> findAllByIsCompletedIsFalse();

    Page<WordQuiz> findAllByUserId(Long userId, Pageable pageable);
}
