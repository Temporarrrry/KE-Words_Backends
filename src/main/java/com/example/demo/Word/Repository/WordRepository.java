package com.example.demo.Word.Repository;

import com.example.demo.Word.Entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<Word, String> {

    @Override
    <S extends Word> S save(S entity);

    void deleteByEnglish(String english);

    @Query(value = "select * from Word order by RAND() limit 1", nativeQuery = true)
    Word findByRandom();

    @Query(value = "select * from Word order by RAND() limit 10", nativeQuery = true)
    List<Word> find10WordsByRandom();
}
