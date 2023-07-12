package com.example.demo.LastWord.Service;

import com.example.demo.LastWord.DTO.LastWordRequestDTO;
import com.example.demo.LastWord.DTO.LastWordResponseDTO;

public interface LastWordService {

    void saveOrUpdate(LastWordRequestDTO lastWordRequestDTO);

    void delete(LastWordRequestDTO lastWordRequestDTO);


    LastWordResponseDTO findByUserId(Long userId);
}
