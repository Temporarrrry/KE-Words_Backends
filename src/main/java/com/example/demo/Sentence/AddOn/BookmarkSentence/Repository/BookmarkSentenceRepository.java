package com.example.demo.Sentence.AddOn.BookmarkSentence.Repository;

import com.example.demo.Sentence.AddOn.BookmarkSentence.Entity.BookmarkSentence;
import com.example.demo.Sentence.AddOn.BookmarkSentence.Entity.BookmarkSentencePk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarkSentenceRepository extends JpaRepository<BookmarkSentence, BookmarkSentencePk> {

    @Override
    <S extends BookmarkSentence> S save(S entity);

    void deleteByUserIdAndSentenceId(Long userId, Long sentenceId);

    boolean existsByUserIdAndSentenceId(Long userId, Long sentenceId);

    Page<BookmarkSentence> findAllByUserId(Long userId, Pageable pageable);
}
