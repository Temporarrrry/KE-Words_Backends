package com.example.demo.Word.AddOn.LastWord.Service;

import com.example.demo.Word.AddOn.LastWord.DTO.LastWordRequestDTO;
import com.example.demo.Word.AddOn.LastWord.DTO.LastWordResponseDTO;

public interface LastWordService {

    void saveOrUpdate(LastWordRequestDTO lastWordRequestDTO);

    LastWordResponseDTO findByUserId(Long userId);
}
