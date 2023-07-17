package com.example.demo.Sentence.AddOn.LastSentence.Service;

import com.example.demo.Sentence.AddOn.LastSentence.DTO.LastSentenceRequestDTO;
import com.example.demo.Sentence.AddOn.LastSentence.DTO.LastSentenceResponseDTO;

public interface LastSentenceService {

    void saveOrUpdate(LastSentenceRequestDTO lastSentenceRequestDTO);

    void delete(LastSentenceRequestDTO lastSentenceRequestDTO);


    LastSentenceResponseDTO findByUserId(Long userId);
}
