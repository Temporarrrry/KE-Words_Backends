package com.example.demo.Word.Service;

import com.example.demo.Word.DTO.WordResponseDTO;

import java.util.List;

public interface WordService {

    //void saveWord(WordRequestDTO wordRequestDTO);
    //void deleteWord(WordRequestDTO wordRequestDTO);

    boolean isExistById(Long wordId);

    WordResponseDTO findById(Long id);

    WordResponseDTO findByEnglish(String english);
    List<WordResponseDTO> findWordsByRandom(int count);
}
