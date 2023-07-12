package com.example.demo.Word.AddOn.LastWord.Service;

import com.example.demo.Word.AddOn.LastWord.DTO.LastWordRequestDTO;
import com.example.demo.Word.AddOn.LastWord.DTO.LastWordResponseDTO;
import com.example.demo.Word.AddOn.LastWord.Entity.LastWord;
import com.example.demo.Word.AddOn.LastWord.Exception.LastWordNotExistException;
import com.example.demo.Word.AddOn.LastWord.Repository.LastWordRepository;
import com.example.demo.Word.Exception.WordNotExistException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class LastWordServiceImpl implements LastWordService {

    private final LastWordRepository lastWordRepository;


    @Override
    public void saveOrUpdate(LastWordRequestDTO lastWordRequestDTO) throws WordNotExistException {
        lastWordRepository.findByUserId(lastWordRequestDTO.getUserId()).ifPresentOrElse(
                lastWord -> lastWord.setWordId(lastWordRequestDTO.getWordId()),
                () -> lastWordRepository.save(lastWordRequestDTO.toEntity())
        );


        lastWordRepository.save(lastWordRequestDTO.toEntity());
    }

    @Override
    public void delete(LastWordRequestDTO lastWordRequestDTO) {
        lastWordRepository.deleteByUserId(lastWordRequestDTO.getUserId());
    }

    @Override
    public LastWordResponseDTO findByUserId(Long userId) {
        LastWord lastWord = lastWordRepository.findByUserId(userId).orElseThrow(LastWordNotExistException::new);
        return new LastWordResponseDTO(lastWord);
    }
}
