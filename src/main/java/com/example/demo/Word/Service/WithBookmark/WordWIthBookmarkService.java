package com.example.demo.Word.Service.WithBookmark;

import com.example.demo.Word.DTO.WordWithBookmarkResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WordWIthBookmarkService {


    WordWithBookmarkResponseDTO findById(Long id);

    List<WordWithBookmarkResponseDTO> findWordsByRandom(int count);

    Page<WordWithBookmarkResponseDTO> findAll(Pageable pageable);
}
