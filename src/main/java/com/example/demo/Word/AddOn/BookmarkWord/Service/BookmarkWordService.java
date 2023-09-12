package com.example.demo.Word.AddOn.BookmarkWord.Service;

import com.example.demo.Word.AddOn.BookmarkWord.DTO.BookmarkWordRequestDTO;
import com.example.demo.Word.AddOn.BookmarkWord.DTO.BookmarkWordResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookmarkWordService {

    void saveBookmarkWord(BookmarkWordRequestDTO bookmarkWordRequestDTO);

    void deleteBookmarkWord(BookmarkWordRequestDTO bookmarkWordRequestDTO);

    Boolean isExist(BookmarkWordRequestDTO bookmarkWordRequestDTO);

    Page<BookmarkWordResponseDTO> findAllByUserId(Long userId, Pageable pageable);
}
