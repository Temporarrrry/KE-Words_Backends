package com.example.demo.Word.Service;

import com.example.demo.Word.Exception.WordExistException;
import com.example.demo.Word.Exception.WordNotExistException;
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
    public void saveWord(WordRequestDTO wordRequestDTO) throws WordExistException {
        if (isExist(wordRequestDTO)) throw new WordExistException();
        wordRepository.save(wordRequestDTO.toEntity());
    }
    @Override
    public void deleteWord(WordRequestDTO wordRequestDTO) throws WordNotExistException {
        if (!isExist(wordRequestDTO)) throw new WordNotExistException();
        wordRepository.deleteByEnglish(wordRequestDTO.getEnglish());
    }

    @Override
    public boolean isExist(WordRequestDTO wordRequestDTO) {
        return wordRepository.existsByEnglish(wordRequestDTO.getEnglish());
    }

    @Override
    public WordResponseDTO findByEnglish(String english) throws WordNotExistException {
        return new WordResponseDTO(wordRepository.findByEnglish(english).orElseThrow(WordNotExistException::new));
    }

    @Override
    public List<WordResponseDTO> findWordsByRandom(int count) {
        return wordRepository.findWordsByRandom(count).stream().map(WordResponseDTO::new).toList();
    }
}
