package com.example.demo.LastWord.Service;

import com.example.demo.LastWord.DTO.LastWordRequestDTO;
import com.example.demo.LastWord.DTO.LastWordResponseDTO;
import com.example.demo.LastWord.Entity.LastWord;
import com.example.demo.LastWord.Exception.LastWordNotExistException;
import com.example.demo.LastWord.Repository.LastWordRepository;
import com.example.demo.Word.Exception.WordNotExistException;
import com.example.demo.Word.Service.WordService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class LastWordServiceImpl implements LastWordService {

    private final LastWordRepository lastWordRepository;

    private final WordService wordService;

    @Override
    public void save(LastWordRequestDTO lastWordRequestDTO) throws WordNotExistException {
        if (!wordService.isExistById(lastWordRequestDTO.getWordId()))
            throw new WordNotExistException();
        lastWordRepository.save(lastWordRequestDTO.toEntity());
    }

    @Override
    public void delete(LastWordRequestDTO lastWordRequestDTO) {
        lastWordRepository.deleteByUserId(lastWordRequestDTO.getUserId());
    }

    @Override
    public void update(LastWordRequestDTO lastWordRequestDTO) {
        LastWord lastWord = lastWordRepository.findByUserId(lastWordRequestDTO.getUserId())
                .orElseThrow(LastWordNotExistException::new);

        lastWord.setWordId(lastWord.getWordId()); //자동으로 update됨
    }

    @Override
    public LastWordResponseDTO findByUserId(Long userId) {
        LastWord lastWord = lastWordRepository.findByUserId(userId).orElseThrow(LastWordNotExistException::new);
        return new LastWordResponseDTO(lastWord);
    }
}
