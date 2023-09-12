package com.example.demo.Sentence.Service.WithBookmark;

import com.example.demo.Sentence.DTO.SentenceWithBookmarkResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SentenceWithBookmarkService {

    SentenceWithBookmarkResponseDTO findById(Long id);

    List<SentenceWithBookmarkResponseDTO> findByRandom(int count);

    Page<SentenceWithBookmarkResponseDTO> findAll(Pageable pageable);
}
