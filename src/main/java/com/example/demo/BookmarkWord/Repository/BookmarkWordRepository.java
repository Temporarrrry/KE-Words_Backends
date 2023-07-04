package com.example.demo.BookmarkWord.Repository;

import com.example.demo.BookmarkWord.Entity.BookmarkWord;
import com.example.demo.BookmarkWord.Entity.BookmarkWordPk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarkWordRepository extends JpaRepository<BookmarkWord, BookmarkWordPk> {

    @Override
    <S extends BookmarkWord> S save(S entity);

    void deleteByUserIdAndEnglish(Long userId, String english);

    boolean existsByUserIdAndEnglish(Long userId, String english);

    Page<BookmarkWord> findAllByUserId(Long userId, Pageable pageable);
}
