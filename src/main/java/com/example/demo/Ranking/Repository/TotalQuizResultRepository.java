package com.example.demo.Ranking.Repository;

import com.example.demo.Ranking.Entity.TotalQuizResultCounter;
import com.example.demo.Ranking.Entity.TotalQuizResultPk;
import com.example.demo.Ranking.Entity.TotalQuizResultType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TotalQuizResultRepository extends JpaRepository<TotalQuizResultCounter, TotalQuizResultPk> {

    @Override
    <S extends TotalQuizResultCounter> S save(S entity);

    Optional<TotalQuizResultCounter> findByUserIdAndType(Long userId, TotalQuizResultType type);
}
