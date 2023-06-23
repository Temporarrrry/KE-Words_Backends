package com.example.demo.Word.Service;

import com.example.demo.Word.dto.WordRequestDTO;
import com.example.demo.Word.dto.WordResponseDTO;

import java.util.List;

public interface WordService {

    void saveWord(WordRequestDTO wordRequestDTO);
    void deleteWord(WordRequestDTO wordRequestDTO);
    WordResponseDTO findWord();
    List<WordResponseDTO> find10Words();
}
