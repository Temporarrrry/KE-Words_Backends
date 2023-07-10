package com.example.demo.Word.BookmarkWord.Service;

import com.example.demo.Word.BookmarkWord.DTO.BookmarkWordRequestDTO;
import com.example.demo.Word.BookmarkWord.DTO.BookmarkWordResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookmarkWordService {

    void saveBookmarkWord(BookmarkWordRequestDTO bookmarkWordRequestDTO);

    void deleteBookmarkWord(BookmarkWordRequestDTO bookmarkWordRequestDTO);

    boolean isExist(BookmarkWordRequestDTO bookmarkWordRequestDTO);

    Page<BookmarkWordResponseDTO> findAllByUserId(Long userId, Pageable pageable);
}
