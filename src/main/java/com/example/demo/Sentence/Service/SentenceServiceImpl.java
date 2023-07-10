package com.example.demo.Sentence.Service;

import com.example.demo.Sentence.DTO.SentenceRequestDTO;
import com.example.demo.Sentence.DTO.SentenceResponseDTO;
import com.example.demo.Sentence.Entity.Sentence;
import com.example.demo.Sentence.Exception.SentenceNotExistException;
import com.example.demo.Sentence.Repository.SentenceRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class SentenceServiceImpl implements SentenceService {
    private final SentenceRepository sentenceRepository;

    public SentenceServiceImpl(SentenceRepository sentenceRepository) {
        this.sentenceRepository = sentenceRepository;
    }
    /*@Override
    public void saveSentence(SentenceRequestDTO sentenceRequestDTO) throws SentenceExistException {
        if (isExist(sentenceRequestDTO)) throw new SentenceExistException();
        sentenceRepository.save(sentenceRequestDTO.toEntity());
    }*/
    /*@Override
    public void deleteSentence(SentenceRequestDTO sentenceRequestDTO) throws SentenceNotExistException {
        if (!isExist(sentenceRequestDTO)) throw new SentenceNotExistException();
        sentenceRepository.deleteByEnglish(sentenceRequestDTO.getEnglish());
    }*/

    @Override
    public boolean isExist(SentenceRequestDTO sentenceRequestDTO) {
        return sentenceRepository.existsByEnglish(sentenceRequestDTO.getEnglish());
    }

    @Override
    public SentenceResponseDTO findById(Long id) {
        Sentence sentence = sentenceRepository.findById(id).orElseThrow(SentenceNotExistException::new);
        return new SentenceResponseDTO(sentence);
    }


    @Override
    public SentenceResponseDTO findByEnglish(String english) throws SentenceNotExistException {
        Sentence sentence = sentenceRepository.findByEnglish(english).orElseThrow(SentenceNotExistException::new);
        return new SentenceResponseDTO(sentence);
    }

    @Override
    public List<SentenceResponseDTO> findByRandom(int count) {
        return sentenceRepository.findByRandom(count).stream().map(SentenceResponseDTO::new).toList();
    }
}
