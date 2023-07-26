package com.example.demo.Sentence.Service;

import com.example.demo.Sentence.DTO.SentenceRequestDTO;
import com.example.demo.Sentence.DTO.SentenceResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SentenceService {

    //void saveSentence(SentenceRequestDTO sentenceRequestDTO);
    //void deleteSentence(SentenceRequestDTO sentenceRequestDTO);

    boolean isExist(SentenceRequestDTO sentenceRequestDTO);

    SentenceResponseDTO findById(Long id);

    SentenceResponseDTO findByEnglish(String english);

    List<SentenceResponseDTO> findByRandom(int count);

    Page<SentenceResponseDTO> findAll(Pageable pageable);
}
