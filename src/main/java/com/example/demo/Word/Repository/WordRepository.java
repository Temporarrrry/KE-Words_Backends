package com.example.demo.Word.Repository;

import com.example.demo.Word.Entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WordRepository extends JpaRepository<Word, String> {

    @Override
    <S extends Word> S save(S entity);

    void deleteByEnglish(String english);

    Optional<Word> findByEnglish(String english);

    @Query(value = "select * from word order by RAND() limit :count", nativeQuery = true)
    List<Word> findWordsByRandom(@Param("count") int count);
}
