package com.example.demo.Word.Service;

import com.example.demo.Word.Repository.WordRepository;
import com.example.demo.Word.dto.WordRequestDTO;
import com.example.demo.Word.dto.WordResponseDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class WordServiceImpl implements WordService {
    private final WordRepository wordRepository;

    public WordServiceImpl(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }
    @Override
    public void saveWord(WordRequestDTO wordRequestDTO) {
        wordRepository.save(wordRequestDTO.toEntity());
    }
    @Override
    public void deleteWord(WordRequestDTO wordRequestDTO) {
        wordRepository.deleteByEnglish(wordRequestDTO.getEnglish());
    }


    @Override
    public WordResponseDTO findWord() {
        return new WordResponseDTO(wordRepository.findByRandom());
    }

    @Override
    public List<WordResponseDTO> find10Words() {
        return wordRepository.find10WordsByRandom().stream().map(WordResponseDTO::new).toList();
    }
}
