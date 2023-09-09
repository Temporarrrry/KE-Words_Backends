package com.example.demo.Sentence.Service;

import com.example.demo.Sentence.DTO.SentenceResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SentenceService {

    SentenceResponseDTO findById(Long id);

    List<SentenceResponseDTO> findByRandom(int count);

    Page<SentenceResponseDTO> findAll(Pageable pageable);
}
