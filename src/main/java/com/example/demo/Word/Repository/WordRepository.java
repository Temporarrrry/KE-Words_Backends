package com.example.demo.Word.Repository;

import com.example.demo.Word.Entity.Word;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {

    Optional<Word> findById(Long id);

    Optional<Word> findByEnglish(String english);

    boolean existsById(Long wordId);

    @Query(value = "select * from word order by RAND() limit :count", nativeQuery = true)
    List<Word> findWordsByRandom(@Param("count") int count);

    Page<Word> findAll(Pageable pageable);

    @Query(value = "select count(*) from word where id <= :id", nativeQuery = true)
    int findIndexById(@Param("id") Long id);
}
