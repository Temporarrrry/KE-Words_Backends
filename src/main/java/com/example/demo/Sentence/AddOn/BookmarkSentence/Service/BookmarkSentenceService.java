package com.example.demo.Sentence.AddOn.BookmarkSentence.Service;

import com.example.demo.Sentence.AddOn.BookmarkSentence.DTO.BookmarkSentenceRequestDTO;
import com.example.demo.Sentence.AddOn.BookmarkSentence.DTO.BookmarkSentenceResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookmarkSentenceService {

    void saveBookmarkSentence(BookmarkSentenceRequestDTO BookmarkSentenceRequestDTO);

    void deleteBookmarkSentence(BookmarkSentenceRequestDTO BookmarkSentenceRequestDTO);

    boolean isExist(BookmarkSentenceRequestDTO bookmarkSentenceRequestDTO);

    Page<BookmarkSentenceResponseDTO> findAllByUserId(Long userId, Pageable pageable);
}
