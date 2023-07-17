package com.example.demo.Sentence.AddOn.LastSentence.Service;

import com.example.demo.Sentence.AddOn.LastSentence.DTO.LastSentenceRequestDTO;
import com.example.demo.Sentence.AddOn.LastSentence.DTO.LastSentenceResponseDTO;
import com.example.demo.Sentence.AddOn.LastSentence.Entity.LastSentence;
import com.example.demo.Sentence.AddOn.LastSentence.Exception.LastSentenceNotExistException;
import com.example.demo.Sentence.AddOn.LastSentence.Repository.LastSentenceRepository;
import com.example.demo.Word.Exception.WordNotExistException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class LastSentenceServiceImpl implements LastSentenceService {

    private final LastSentenceRepository lastSentenceRepository;


    @Override
    public void saveOrUpdate(LastSentenceRequestDTO lastSentenceRequestDTO) throws WordNotExistException {
        lastSentenceRepository.findByUserId(lastSentenceRequestDTO.getUserId()).ifPresentOrElse(
                lastSentence -> lastSentence.setSentenceId(lastSentenceRequestDTO.getSentenceId()),
                () -> lastSentenceRepository.save(lastSentenceRequestDTO.toEntity())
        );


        lastSentenceRepository.save(lastSentenceRequestDTO.toEntity());
    }

    @Override
    public void delete(LastSentenceRequestDTO lastSentenceRequestDTO) {
        lastSentenceRepository.deleteByUserId(lastSentenceRequestDTO.getUserId());
    }

    @Override
    public LastSentenceResponseDTO findByUserId(Long userId) {
        LastSentence lastSentence = lastSentenceRepository.findByUserId(userId).orElseThrow(LastSentenceNotExistException::new);
        return new LastSentenceResponseDTO(lastSentence);
    }
}
