package com.example.demo.Word.AddOn.BookmarkWord.Repository;

import com.example.demo.Word.AddOn.BookmarkWord.Entity.BookmarkWord;
import com.example.demo.Word.AddOn.BookmarkWord.Entity.BookmarkWordPk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarkWordRepository extends JpaRepository<BookmarkWord, BookmarkWordPk> {

    @Override
    <S extends BookmarkWord> S save(S entity);

    void deleteByUserIdAndWordId(Long userId, Long wordId);

    boolean existsByUserIdAndWordId(Long userId, Long wordId);

    Page<BookmarkWord> findAllByUserId(Long userId, Pageable pageable);
}
