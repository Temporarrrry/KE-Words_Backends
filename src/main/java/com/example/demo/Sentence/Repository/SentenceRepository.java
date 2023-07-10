package com.example.demo.Sentence.Repository;

import com.example.demo.Sentence.Entity.Sentence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SentenceRepository extends JpaRepository<Sentence, Long> {

    @Override
    <S extends Sentence> S save(S entity);

    void deleteByEnglish(String english);

    @Override
    Optional<Sentence> findById(Long id);

    Optional<Sentence> findByEnglish(String english);

    @Query(value = "select * from sentence order by RAND() limit :count", nativeQuery = true)
    List<Sentence> findByRandom(@Param("count") int count);



    boolean existsByEnglish(String english);
}
