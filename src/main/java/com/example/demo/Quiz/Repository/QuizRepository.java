package com.example.demo.Quiz.Repository;

import com.example.demo.Quiz.Entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, String> {

    @Override
    <S extends Quiz> S save(S entity);

    void deleteById(Long id);

    Quiz findById(Long id);

    List<Quiz> findAllByUserId(Long userId);
}
