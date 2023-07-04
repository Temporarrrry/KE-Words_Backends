package com.example.demo.Quiz.Repository;

import com.example.demo.Quiz.Entity.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    @Override
    <S extends Quiz> S save(S entity);

    void deleteById(Long id);

    boolean existsById(Long id);

    Optional<Quiz> findById(Long id);

    Page<Quiz> findAllByUserId(Long userId, Pageable pageable);
}
