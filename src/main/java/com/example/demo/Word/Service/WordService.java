package com.example.demo.Word.Service;

import com.example.demo.Word.DTO.PageNumberResponseDTO;
import com.example.demo.Word.DTO.WordResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WordService {

    //void saveWord(WordRequestDTO wordRequestDTO);
    //void deleteWord(WordRequestDTO wordRequestDTO);

    boolean isExistById(Long wordId);

    WordResponseDTO findById(Long id);

    WordResponseDTO findByEnglish(String english);
    List<WordResponseDTO> findWordsByRandom(int count);

    PageNumberResponseDTO findPageNumberById(Long id, int pageSize);

    Page<WordResponseDTO> findAll(Pageable pageable);

    PageNumberResponseDTO findLastPage(Pageable pageable);
}
