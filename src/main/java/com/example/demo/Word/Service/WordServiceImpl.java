package com.example.demo.Word.Service;

import com.example.demo.Member.Service.MemberService;
import com.example.demo.Word.AddOn.BookmarkWord.DTO.BookmarkWordRequestDTO;
import com.example.demo.Word.AddOn.BookmarkWord.Service.BookmarkWordService;
import com.example.demo.Word.AddOn.LastWord.DTO.LastWordRequestDTO;
import com.example.demo.Word.AddOn.LastWord.Service.LastWordService;
import com.example.demo.Word.DTO.WordResponseDTO;
import com.example.demo.Word.Entity.Word;
import com.example.demo.Word.Exception.WordNotExistException;
import com.example.demo.Word.Repository.WordRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class WordServiceImpl implements WordService {
    private final WordRepository wordRepository;

    private final BookmarkWordService bookmarkWordService;

    private final MemberService memberService;

    private final LastWordService lastWordService;

    @Override
    public WordResponseDTO findById(Long id) {
        Word word = wordRepository.findById(id).orElseThrow(WordNotExistException::new);
        Long userId = memberService.findIdByAuthentication();

        return new WordResponseDTO(
                word, bookmarkWordService.isExist(new BookmarkWordRequestDTO(userId, word.getId()))
        );
    }

    @Override
    public List<WordResponseDTO> findWordsByRandom(int count) {
        Long userId = memberService.findIdByAuthentication();

        return wordRepository
                .findWordsByRandom(count)
                .stream().map(
                        word -> new WordResponseDTO(
                                word,
                                bookmarkWordService.isExist(new BookmarkWordRequestDTO(userId, word.getId()))
                        )
                ).toList();
    }

    @Override
    public Page<WordResponseDTO> findAll(Pageable pageable) {
        Long userId = memberService.findIdByAuthentication();

        Page<WordResponseDTO> words = wordRepository.findAll(pageable).map(
                word -> new WordResponseDTO(
                        word,
                        bookmarkWordService.isExist(new BookmarkWordRequestDTO(userId, word.getId()))
                )
        );

        // 마지막 단어 저장
        if (words.hasContent()) { // page 내에 단어가 존재할 때
            Long firstId = words.getContent().get(0).getId();
            lastWordService.saveOrUpdate(new LastWordRequestDTO(userId, firstId));
        }

        return words;
    }
}
