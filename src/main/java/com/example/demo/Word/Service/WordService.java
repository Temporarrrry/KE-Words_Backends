package com.example.demo.Word.Service;

import com.example.demo.Word.DTO.WordRequestDTO;
import com.example.demo.Word.DTO.WordResponseDTO;

import java.util.List;

public interface WordService {

    //void saveWord(WordRequestDTO wordRequestDTO);
    //void deleteWord(WordRequestDTO wordRequestDTO);

    boolean isExist(WordRequestDTO wordRequestDTO);

    WordResponseDTO findById(Long id);

    WordResponseDTO findByEnglish(String english);
    List<WordResponseDTO> findWordsByRandom(int count);
}
