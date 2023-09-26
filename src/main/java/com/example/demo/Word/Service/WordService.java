package com.example.demo.Word.Service;

import com.example.demo.Word.DTO.WordResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WordService {

    WordResponseDTO findById(Long id);

    List<WordResponseDTO> findWordsByRandom(int count);


    Page<WordResponseDTO> findAll(Pageable pageable);
}
