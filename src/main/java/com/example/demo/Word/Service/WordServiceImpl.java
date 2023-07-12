package com.example.demo.Word.Service;

import com.example.demo.BookmarkWord.DTO.BookmarkWordRequestDTO;
import com.example.demo.BookmarkWord.Service.BookmarkWordService;
import com.example.demo.Member.Service.MemberService;
import com.example.demo.Word.DTO.WordResponseDTO;
import com.example.demo.Word.Entity.Word;
import com.example.demo.Word.Exception.WordNotExistException;
import com.example.demo.Word.Repository.WordRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class WordServiceImpl implements WordService {
    private final WordRepository wordRepository;

    private final BookmarkWordService bookmarkWordService;

    private final MemberService memberService;

    /*@Override
    public void saveWord(WordRequestDTO wordRequestDTO) throws WordExistException {
        if (isExist(wordRequestDTO)) throw new WordExistException();
        wordRepository.save(wordRequestDTO.toEntity());
    }*/
    /*@Override
    public void deleteWord(WordRequestDTO wordRequestDTO) throws WordNotExistException {
        if (!isExist(wordRequestDTO)) throw new WordNotExistException();
        wordRepository.deleteByEnglish(wordRequestDTO.getEnglish());
    }*/

    @Override
    public boolean isExistById(Long wordId) {
        return wordRepository.existsById(wordId);
    }

    @Override
    public WordResponseDTO findById(Long id) {
        Word word = wordRepository.findById(id).orElseThrow(WordNotExistException::new);
        Long userId = memberService.findIdByAuthentication();

        return new WordResponseDTO(
                word, bookmarkWordService.isExist(new BookmarkWordRequestDTO(userId, word.getId()))
        );
    }

    @Override
    public WordResponseDTO findByEnglish(String english) throws WordNotExistException {
        Long userId = memberService.findIdByAuthentication();
        Word word = wordRepository.findByEnglish(english).orElseThrow(WordNotExistException::new);

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
}
